package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.BaseDao;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.pool.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final String SELECT_USERS_PASSWORD_AND_ROLE = "SELECT user_password, user_role FROM users WHERE user_login = ?";

    @Override
    public boolean insert(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public Optional<UserRole> authenticate(String login, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USERS_PASSWORD_AND_ROLE)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            String roleFromDB;
            if (resultSet.next()) {
                passFromDb = resultSet.getString("user_password");
                if(password.equals(passFromDb)){
                    roleFromDB=resultSet.getString("user_role");
                    return Optional.of(UserRole.valueOf(roleFromDB.toUpperCase()));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Authentication exception. "+e.getMessage());
        }
        return Optional.empty();
    }
}
