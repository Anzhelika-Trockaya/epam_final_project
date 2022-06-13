package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.UserDaoImpl;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.util.PasswordEncryptor;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<User> authenticate(String login, String password) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectLogin(login) || !validator.isCorrectPassword(password)) {
            return Optional.empty();
        }
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            String encryptedPassword = PasswordEncryptor.encrypt(password);
            return userDao.authenticate(login, encryptedPassword);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when authenticate user. Login:'" + login + "', password:'" + password + "'",
                    daoException);
            throw new ServiceException("Exception when authenticate user. Login:'" + login + "', password:'" +
                    password + "'", daoException);
        }
    }

    @Override
    public boolean create(Map<String, String> userData) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectRegisterData(userData)) {
            return false;
        }
        UserDaoImpl userDao = new UserDaoImpl();
        User user = buildUser(userData);
        String password = user.getPassword();
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        user.setPassword(encryptedPassword);
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            String login = userData.get(USER_LOGIN);
            if (userDao.findByLogin(login).isPresent()) {
                userData.put(AttributeName.INCORRECT_LOGIN, PropertyKey.REGISTRATION_NOT_UNIQUE_LOGIN);
                return false;
            }
            return userDao.create(user);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when create user." + user, daoException);
            throw new ServiceException("Exception when create user." + user, daoException);
        }
    }

    @Override
    public boolean deleteById(String idString) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        long idValue = Long.parseLong(idString);
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.deleteById(idValue);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when remove user. id=" + idString + " " + daoException);
            throw new ServiceException("Exception when remove user. id=" + idString, daoException);
        }
    }

    @Override
    public boolean changeState(String idString, String stateString) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectState(stateString)) {
            return false;
        }
        UserDaoImpl userDao = new UserDaoImpl();
        long idValue = Long.parseLong(idString);
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
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
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all users.", e);
            throw new ServiceException("Exception when find all users.", e);
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
