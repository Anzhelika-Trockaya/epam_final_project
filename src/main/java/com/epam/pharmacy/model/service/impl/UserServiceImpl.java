package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.Parameter;
import com.epam.pharmacy.model.dao.DaoProvider;
import com.epam.pharmacy.model.dao.UserDao;
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
import static com.epam.pharmacy.controller.Parameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<UserRole> authenticate(String login, String password) throws ServiceException {
        Validator validator = ValidatorImpl.getInstance();
        if (!validator.isCorrectLogin(login) || !validator.isCorrectPassword(password)) {
            return Optional.empty();
        }
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao();
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        try {
            return userDao.authenticate(login, encryptedPassword);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when authenticate user. Login:'" + login + "', password:'" + password + "'",
                    daoException);
            throw new ServiceException(daoException);
        }
    }

    @Override
    public boolean create(Map<String, String> userData) throws ServiceException {
        Validator validator = ValidatorImpl.getInstance();
        if (!validator.isCorrectRegisterData(userData)) {
            return false;
        }
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao();
        User user = buildUser(userData);
        String password = user.getPassword();
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        user.setPassword(encryptedPassword);
        try {
            return userDao.create(user);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when create user." + user, daoException);
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao();
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all users."+e);
            throw new ServiceException(e);
        }
    }

    private User buildUser(Map<String, String> userData){
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
                buildLastname(lastname).buildName(name).buildPatronymic(patronymic).
                buildPassword(password).buildSex(sex).buildBirthdayDate(birthdayDate).
                buildPhone(phone).buildAddress(address);
        return builder.build();
    }
}
