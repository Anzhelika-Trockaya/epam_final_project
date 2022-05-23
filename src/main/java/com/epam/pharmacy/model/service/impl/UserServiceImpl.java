package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.dao.impl.UserDaoImpl;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.util.PasswordEncryptor;
import com.epam.pharmacy.validator.Validator;
import com.epam.pharmacy.validator.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.ParameterName.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<User> authenticate(String login, String password) throws ServiceException {
        Validator validator = ValidatorImpl.getInstance();
        if (!validator.isCorrectLogin(login) || !validator.isCorrectPassword(password)) {
            return Optional.empty();
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.beginWithAutoCommit(userDao);
            String encryptedPassword = PasswordEncryptor.encrypt(password);
            return userDao.authenticate(login, encryptedPassword);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when authenticate user. Login:'" + login + "', password:'" + password + "'",
                    daoException);
            throw new ServiceException("Exception when authenticate user. Login:'" + login + "', password:'" +
                    password + "'", daoException);
        } finally {
                transaction.end();
        }
    }

    @Override
    public boolean create(Map<String, String> userData) throws ServiceException {
        Validator validator = ValidatorImpl.getInstance();
        if (!validator.isCorrectRegisterData(userData)) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        User user = buildUser(userData);
        String password = user.getPassword();
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        user.setPassword(encryptedPassword);
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.beginWithAutoCommit(userDao);
            String login = userData.get(ParameterName.USER_LOGIN);
            if (userDao.findByLogin(login).isPresent()) {
                userData.put(AttributeName.INCORRECT_LOGIN, PropertyKey.REGISTRATION_NOT_UNIQUE_LOGIN);
                return false;
            }
            boolean created = userDao.create(user);
            transaction.commit();
            return created;
        } catch (DaoException daoException) {
            LOGGER.error("Exception when create user." + user, daoException);
                transaction.rollback();
            throw new ServiceException("Exception when create user." + user, daoException);
        } finally {
                transaction.end();
        }
    }

    @Override
    public boolean deleteById(String idString) throws ServiceException {//FIXME TRANSACTION
        Validator validator = ValidatorImpl.getInstance();
        if (!validator.isCorrectId(idString)) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        long idValue;
        try {
            idValue = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw new ServiceException("Exception when remove user. Incorrect id=" + idString);
        }
        try {
            return userDao.deleteById(idValue);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when remove user. id=" + idString + " " + daoException);
            throw new ServiceException("Exception when remove user. id=" + idString, daoException);
        }
    }

    @Override
    public boolean changeState(String idString, String stateString) throws ServiceException {
        Validator validator = ValidatorImpl.getInstance();
        if (!validator.isCorrectId(idString) || !validator.isCorrectState(stateString)) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        long idValue;
        try {
            idValue = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw new ServiceException("Exception when change user state. Incorrect id=" + idString);
        }
        try {
            Optional<User> optionalUser = userDao.findById(idValue);
            if (!optionalUser.isPresent()) {
                return false;
            }
            User.State state = User.State.valueOf(stateString);
            return userDao.changeState(idValue, state);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when change user state id=" + idString + " state=" + stateString + " " +
                    daoException);
            throw new ServiceException("Exception when change user state id=" + idString + " state=" + stateString +
                    " ", daoException);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all users." + e);
            throw new ServiceException(e);
        }
    }

    private User buildUser(Map<String, String> userData) {
        String login = userData.get(USER_LOGIN);
        String roleString = userData.get(USER_ROLE);
        UserRole role = UserRole.valueOf(roleString);
        String password = userData.get(USER_PASSWORD);
        String lastname = userData.get(USER_LASTNAME);
        String name = userData.get(USER_NAME);
        String patronymic = userData.get(USER_PATRONYMIC);
        String sexString = userData.get(USER_SEX);
        User.Sex sex = User.Sex.valueOf(sexString);
        LocalDate birthdayDate = LocalDate.parse(userData.get(USER_BIRTHDAY_DATE));
        String phone = userData.get(USER_PHONE);
        String address = userData.get(USER_ADDRESS);
        User.Builder builder = new User.Builder(login, role).
                buildState(User.State.ACTIVE).
                buildLastname(lastname).buildName(name).buildPatronymic(patronymic).
                buildPassword(password).buildSex(sex).buildBirthdayDate(birthdayDate).
                buildPhone(phone).buildAddress(address);
        return builder.build();
    }
}
