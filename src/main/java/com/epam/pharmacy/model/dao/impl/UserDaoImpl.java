package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.mapper.impl.UserRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.USER_PASSWORD;
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
    private static final String SQL_SELECT_USER_BY_ID =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address, user_state FROM users WHERE user_id = ?";
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (user_login, user_password, user_lastname, user_name, user_patronymic, " +
                    "user_birthday_date, user_sex, user_role, user_phone, user_address, user_state) values(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER_STATE = "UPDATE users SET user_state = ? WHERE user_id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";

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
            LOGGER.error("Adding user exception. " + e.getMessage());
            throw new DaoException("Adding user exception. ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when remove user id=" + id + e);
            throw new DaoException("Exception when remove user id=" + id, e);
        }
    }

    @Override
    public boolean changeState(long id, User.State state) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_STATE)) {
            statement.setString(1, state.name());
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when change state user id=" + id + " state=" + state + e);
            throw new DaoException("Exception when change state user id=" + id + " state=" + state, e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            UserRowMapper mapper = UserRowMapper.getInstance();
            Optional<User> currentUserOptional;
            while (resultSet.next()) {
                currentUserOptional = mapper.mapRow(resultSet);
                currentUserOptional.ifPresent(users::add);
            }
        } catch (SQLException e) {
            LOGGER.error("Find all users exception. " + e.getMessage());
            throw new DaoException("Find all users exception. ", e);
        }
        return users;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;//fixme
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
            LOGGER.error("Authentication exception. " + e.getMessage());
            throw new DaoException("Authentication exception. ", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            return findUser(statement);
        } catch (SQLException e) {
            LOGGER.error("Find user by id exception. " + e.getMessage());
            throw new DaoException("Find user by id exception. ", e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            return findUser(statement);
        } catch (SQLException e) {
            LOGGER.error("Find user by id exception. " + e.getMessage());
            throw new DaoException("Find user by id exception. ", e);
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
}
