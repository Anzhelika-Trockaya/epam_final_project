package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.mapper.impl.UserRowMapper;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.pharmacy.controller.Parameter.*;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SELECT_USER_PASSWORD_AND_ROLE =
            "SELECT user_password, user_role FROM users WHERE user_login = ?";
    private static final String SELECT_ALL_USERS =
            "SELECT user_id, user_login, user_password, user_lastname, user_name, user_patronymic, user_birthday_date, " +
                    "user_sex, user_role, user_phone, user_address FROM users";
    private static final String INSERT_USER =
            "INSERT INTO users (user_login, user_password, user_lastname, user_name, user_patronymic, " +
                    "user_birthday_date, user_sex, user_role, user_phone, user_address) values(?,?,?,?,?,?,?,?,?,?)";

    @Override
    public boolean create(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
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
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding user exception. " + e.getMessage());
            throw new DaoException("Adding user exception. " + e.getMessage());
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            UserRowMapper mapper = UserRowMapper.getInstance();
            Optional<User> currentUserOptional;
            if (resultSet.next()) {
                currentUserOptional=mapper.mapRow(resultSet);
                currentUserOptional.ifPresent(users::add);
            }
        } catch (SQLException e) {
            LOGGER.error("Find all users exception. " + e.getMessage());
            throw new DaoException("Find all users exception. " + e.getMessage());
        }
        return users;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public Optional<UserRole> authenticate(String login, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_PASSWORD_AND_ROLE)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            String roleFromDB;
            if (resultSet.next()) {
                passFromDb = resultSet.getString(USER_PASSWORD);
                if (password.equals(passFromDb)) {
                    roleFromDB = resultSet.getString(USER_ROLE);
                    return Optional.of(UserRole.valueOf(roleFromDB.toUpperCase()));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Authentication exception. " + e.getMessage());
            throw new DaoException("Authentication exception. " + e.getMessage());
        }
        return Optional.empty();
    }
}
