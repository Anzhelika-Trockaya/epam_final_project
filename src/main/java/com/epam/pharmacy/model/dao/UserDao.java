package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    Optional<UserRole> authenticate(String login, String password) throws DaoException;
}
