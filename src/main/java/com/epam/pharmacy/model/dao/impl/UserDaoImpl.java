package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.mapper.impl.UserRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.USER_LASTNAME;
import static com.epam.pharmacy.controller.AttributeName.USER_PASSWORD;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.model.dao.ColumnName.USER_STATE;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SQL_SELECT_ALL_USERS =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address, user_state FROM users";
    private static final String SQL_SELECT_PASSWORD_AND_STATE_BY_LOGIN =
            "SELECT user_password, user_state FROM users WHERE user_login = ?";
    private static final String SQL_SELECT_USER_BY_LOGIN =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address, user_state FROM users WHERE user_login = ?";
    private static final String SQL_SELECT_USER_WITH_ROLE_CUSTOMER_AND_STATE_ACTIVE =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address, user_state FROM users " +
                    "WHERE user_role = 'CUSTOMER' AND user_state = 'ACTIVE'";
    private static final String SQL_SELECT_USER_BY_ID =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address, user_state FROM users WHERE user_id = ?";
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (user_login, user_password, user_lastname, user_name, user_patronymic, " +
                    "user_birthday_date, user_sex, user_role, user_phone, user_address, user_state, " +
                    "user_account_balance) VALUES (?,?,?,?,?,?,?,?,?,?,?, 300.00)";
    private static final String SQL_UPDATE_USER_DATA =
            "UPDATE users SET user_lastname = ?, user_name = ?, user_patronymic = ?, user_birthday_date = ?, " +
                    "user_sex = ?, user_phone = ?, user_address = ? WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_STATE = "UPDATE users SET user_state = ? WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET user_password = ? WHERE user_id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_USER_BY_LASTNAME_NAME_PATRONYMIC_BIRTHDAY_DATE_USER_ROLE_USER_STATE =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address, user_state FROM users " +
                    "WHERE LOWER(user_lastname) LIKE LOWER(?) AND LOWER(user_name) LIKE LOWER(?) AND " +
                    "LOWER(user_patronymic) LIKE LOWER(?) AND user_birthday_date LIKE ? AND " +
                    "user_role LIKE ? AND user_state LIKE ?";
    private static final String SQL_SELECT_ACCOUNT_BALANCE_BY_USER_ID =
            "SELECT user_account_balance FROM users WHERE user_id = ?";
    private static final String SQL_INCREASE_ACCOUNT_BALANCE_BY_USER_ID =
            "UPDATE users SET user_account_balance = user_account_balance + ? WHERE user_id = ?";
    private static final BigDecimal BALANCE_IF_NOT_FOUND = BigDecimal.ZERO;

    @Override
    public boolean create(User user) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getName());
            statement.setString(5, user.getPatronymic());
            statement.setDate(6, Date.valueOf(user.getBirthdayDate()));
            statement.setString(7, user.getSex().name());
            statement.setString(8, user.getRole().name());
            statement.setString(9, user.getPhone());
            statement.setString(10, user.getAddress());
            statement.setString(11, user.getState().name());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding user exception.", e);
            throw new DaoException("Adding user exception.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when remove user id=" + id, e);
            throw new DaoException("Exception when remove user id=" + id, e);
        }
    }

    @Override
    public boolean updateState(long id, User.State state) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_STATE)) {
            statement.setString(1, state.name());
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when change state user id=" + id + " state=" + state, e);
            throw new DaoException("Exception when change state user id=" + id + " state=" + state, e);
        }
    }

    @Override
    public boolean updatePassword(long id, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            statement.setString(1, password);
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when update password user id=" + id + " password=" + password, e);
            throw new DaoException("Exception when update password user id=" + id + " password=" + password, e);
        }
    }

    @Override
    public List<User> findActiveCustomers() throws DaoException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_WITH_ROLE_CUSTOMER_AND_STATE_ACTIVE);
             ResultSet resultSet = statement.executeQuery()) {
            return extractUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Find active customers exception.", e);
            throw new DaoException("Find active customers exception.", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            return extractUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Find all users exception. ", e);
            throw new DaoException("Find all users exception. ", e);
        }
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> oldUserOptional = findById(user.getId());
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_DATA)) {
            statement.setString(1, user.getLastname());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPatronymic());
            statement.setDate(4, Date.valueOf(user.getBirthdayDate()));
            statement.setString(5, user.getSex().name());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getAddress());
            statement.setLong(8, user.getId());
            return statement.executeUpdate() == ONE_UPDATED ? oldUserOptional : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Exception when update user." + user, e);
            throw new DaoException("Exception when update user." + user, e);
        }
    }

    @Override
    public Optional<User> authenticate(String login, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PASSWORD_AND_STATE_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                String passFromDb;
                String stateFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(USER_PASSWORD);
                    stateFromDb = resultSet.getString(USER_STATE);
                    if (password.equals(passFromDb) && stateFromDb.equals(User.State.ACTIVE.name())) {
                        return findByLogin(login);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Authentication exception.", e);
            throw new DaoException("Authentication exception.", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            return findUser(statement);
        } catch (SQLException e) {
            LOGGER.error("Find user by id exception.", e);
            throw new DaoException("Find user by id exception.", e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            return findUser(statement);
        } catch (SQLException e) {
            LOGGER.error("Find user by id exception.", e);
            throw new DaoException("Find user by id exception.", e);
        }
    }

    @Override
    public BigDecimal findAccountBalance(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BALANCE_BY_USER_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                } else {
                    LOGGER.warn("User account balance not found. id=" + id);
                    return BALANCE_IF_NOT_FOUND;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Find user account balance by id exception. id=" + id, e);
            throw new DaoException("Find user account balance by id exception. id=" + id, e);
        }
    }

    @Override
    public boolean increaseAccountBalance(long id, BigDecimal value) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INCREASE_ACCOUNT_BALANCE_BY_USER_ID)) {
            statement.setBigDecimal(1, value);
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Increase user account balance by id exception. id=" + id +
                    ", value=" + value, e);
            throw new DaoException("Increase user account balance by id exception. id=" + id +
                    ", value=" + value, e);
        }
    }

    @Override
    public boolean decreaseAccountBalance(long id, BigDecimal value) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INCREASE_ACCOUNT_BALANCE_BY_USER_ID)) {
            statement.setBigDecimal(1, value.negate());
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Decrease user account balance by id exception. id=" + id +
                    ", value=" + value, e);
            throw new DaoException("Decrease user account balance by id exception. id=" + id +
                    ", value=" + value, e);
        }
    }

    public List<User> findByParams(Map<String, String> paramsMap) throws DaoException {
        try (PreparedStatement statement = connection.
                prepareStatement(SQL_SELECT_USER_BY_LASTNAME_NAME_PATRONYMIC_BIRTHDAY_DATE_USER_ROLE_USER_STATE)) {
            statement.setString(1, paramsMap.get(USER_LASTNAME));
            statement.setString(2, paramsMap.get(USER_NAME));
            statement.setString(3, paramsMap.get(USER_PATRONYMIC));
            statement.setString(4, paramsMap.get(USER_BIRTHDAY_DATE));
            statement.setString(5, paramsMap.get(USER_ROLE));
            statement.setString(6, paramsMap.get(USER_STATE));
            try (ResultSet resultSet = statement.executeQuery()) {
                return extractUsersFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when find users by params." + paramsMap, e);
            throw new DaoException("Exception when find users by params." + paramsMap, e);
        }
    }

    private Optional<User> findUser(PreparedStatement statement) throws SQLException, DaoException {
        try (ResultSet resultSet = statement.executeQuery()) {
            UserRowMapper mapper = UserRowMapper.getInstance();
            Optional<User> userOptional;
            if (resultSet.next()) {
                userOptional = mapper.mapRow(resultSet);
            } else {
                userOptional = Optional.empty();
            }
            return userOptional;
        }
    }

    private List<User> extractUsersFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
        List<User> users = new ArrayList<>();
        UserRowMapper mapper = UserRowMapper.getInstance();
        Optional<User> currentUserOptional;
        while (resultSet.next()) {
            currentUserOptional = mapper.mapRow(resultSet);
            currentUserOptional.ifPresent(users::add);
        }
        return users;
    }
}
