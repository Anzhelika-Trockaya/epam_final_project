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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.NEW_PASSWORD;
import static com.epam.pharmacy.controller.ParameterName.OLD_PASSWORD;

/**
 * The type User service.
 */
class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * The constant LEGAL_AGE.
     */
    public static final int LEGAL_AGE = 18;
    private static final String PERCENT = "%";
    private static final char DELIMITER = ' ';

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
        User user = buildUserWithState(userData, User.State.ACTIVE);
        if (!isOfLegalAge(user.getBirthdayDate())) {
            userData.put(INCORRECT_BIRTHDAY_DATE, PropertyKey.REGISTRATION_INCORRECT_BIRTHDAY_DATE);
            return false;
        }
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
    public boolean changeState(String idString, String stateString) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectUserState(stateString)) {
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
            return userDao.updateState(idValue, state);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when change user state id=" + idString + " state=" + stateString,
                    daoException);
            throw new ServiceException("Exception when change user state id=" + idString + " state=" + stateString,
                    daoException);
        }
    }

    @Override
    public boolean updatePassword(long userId, Map<String, String> passwordData) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectChangePasswordData(passwordData)) {
            return false;
        }
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            Optional<User> optionalUser = userDao.findById(userId);
            if (!optionalUser.isPresent()) {
                LOGGER.warn("User not found. id=" + userId);
                return false;
            }
            User user = optionalUser.get();
            String encryptedOldPasswordFromPage = PasswordEncryptor.encrypt(passwordData.get(OLD_PASSWORD));
            if (encryptedOldPasswordFromPage.equals(user.getPassword())) {
                String encryptedNewPassword = PasswordEncryptor.encrypt(passwordData.get(NEW_PASSWORD));
                return userDao.updatePassword(userId, encryptedNewPassword);
            } else {
                return false;
            }
        } catch (DaoException daoException) {
            LOGGER.error("Exception when update password user id=" + userId, daoException);
            throw new ServiceException("Exception when update password user id=" + userId, daoException);
        }
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.findById(id);
        } catch (DaoException daoException) {
            LOGGER.error("Exception when find user id=" + id, daoException);
            throw new ServiceException("Exception when find user id=" + id, daoException);
        }
    }

    @Override
    public List<User> findAllExceptId(long id) throws ServiceException {
        List<User> users = findAll();
        users.removeIf(user -> user.getId() == id);
        return users;
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

    @Override
    public List<User> findActiveCustomers() throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.findActiveCustomers();
        } catch (DaoException e) {
            LOGGER.error("Exception when find active customers.", e);
            throw new ServiceException("Exception when find active customers.", e);
        }
    }

    @Override
    public Optional<User> updateUserInfo(Map<String, String> userData) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectUserPersonalData(userData)) {
            return Optional.empty();
        }
        User user = buildUserWithState(userData, null);
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error("Exception when update user." + user, e);
            throw new ServiceException("Exception when update user." + user, e);
        }
    }

    @Override
    public List<User> findByParams(Map<String, String> paramsMap) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectUserSearchParamsMap(paramsMap)) {
            return new ArrayList<>();
        }
        replaceUserParamsBySqlRegexes(paramsMap);
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.findByParams(paramsMap);
        } catch (DaoException e) {
            LOGGER.error("Exception when search users. paramsMap=" + paramsMap, e);
            throw new ServiceException("Exception when search users. paramsMap=" + paramsMap, e);
        }
    }

    @Override
    public BigDecimal findUserAccountBalance(long customerId) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.findAccountBalance(customerId);
        } catch (DaoException e) {
            LOGGER.error("Exception when find account balance. CustomerId=" + customerId, e);
            throw new ServiceException("Exception when find account balance. CustomerId=" + customerId, e);
        }
    }

    @Override
    public boolean depositToCustomerAccount(long customerId, String value) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectDepositValue(value)) {
            return false;
        }
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            return userDao.increaseAccountBalance(customerId, new BigDecimal(value));
        } catch (DaoException e) {
            LOGGER.error("Exception when increase account balance. CustomerId=" + customerId +
                    ", value=" + value, e);
            throw new ServiceException("Exception when increase account balance. CustomerId=" + customerId +
                    ", value=" + value, e);
        }
    }

    @Override
    public String findUserFullName(long id) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(userDao);
            Optional<User> optionalUser = userDao.findById(id);
            if (!optionalUser.isPresent()) {
                LOGGER.warn("User with id=" + id + " not found");
                throw new ServiceException("User with id=" + id + " not found");
            }
            User user = optionalUser.get();
            StringBuilder fullNameBuilder = new StringBuilder(user.getLastname());
            fullNameBuilder.append(DELIMITER).append(user.getName()).append(DELIMITER).append(user.getPatronymic());
            return fullNameBuilder.toString();
        } catch (DaoException e) {
            LOGGER.error("Exception when find user full name. Id=" + id, e);
            throw new ServiceException("Exception when find user full name. Id=" + id, e);
        }
    }

    private void replaceUserParamsBySqlRegexes(Map<String, String> paramsMap) {
        replaceParamsValuesByRegex(paramsMap, USER_LASTNAME, USER_NAME, USER_PATRONYMIC);
        replaceEmptyParamsByPercent(paramsMap, USER_BIRTHDAY_DATE, USER_ROLE, USER_STATE);
    }

    private void replaceParamsValuesByRegex(Map<String, String> paramsMap, String... paramsNames) {
        String paramValue;
        String paramRegex;
        for (String paramName : paramsNames) {
            paramValue = paramsMap.get(paramName);
            paramRegex = paramValue.isEmpty() ? PERCENT : PERCENT + paramValue + PERCENT;
            paramsMap.put(paramName, paramRegex);
        }
    }

    private void replaceEmptyParamsByPercent(Map<String, String> paramsMap, String... paramsNames) {
        for (String paramName : paramsNames) {
            if (paramsMap.get(paramName).isEmpty()) {
                paramsMap.put(paramName, PERCENT);
            }
        }
    }

    private boolean isOfLegalAge(LocalDate birthdayDate) {
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdayDate, now);
        int age = period.getYears();
        return age >= LEGAL_AGE;
    }

    private User buildUserWithState(Map<String, String> userData, User.State state) {
        String login = userData.get(USER_LOGIN);
        String roleString = userData.get(USER_ROLE);
        UserRole role = roleString != null ? UserRole.valueOf(roleString) : null;
        String password = userData.get(USER_PASSWORD);
        String lastname = userData.get(USER_LASTNAME);
        String name = userData.get(USER_NAME);
        String patronymic = userData.get(USER_PATRONYMIC);
        String sexString = userData.get(USER_SEX);
        User.Sex sex = User.Sex.valueOf(sexString);
        LocalDate birthdayDate = LocalDate.parse(userData.get(USER_BIRTHDAY_DATE));
        String phone = userData.get(USER_PHONE);
        String address = userData.get(USER_ADDRESS);
        User.Builder builder;
        String idString = userData.get(USER_ID);
        if (idString != null) {
            long id = Long.parseLong(idString);
            builder = new User.Builder(id, login, role);
        } else {
            builder = new User.Builder(login, role);
        }
        builder.buildState(state).
                buildLastname(lastname).
                buildName(name).
                buildPatronymic(patronymic).
                buildPassword(password).
                buildSex(sex).
                buildBirthdayDate(birthdayDate).
                buildPhone(phone).
                buildAddress(address);
        return builder.build();
    }


}
