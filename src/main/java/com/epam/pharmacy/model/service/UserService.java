package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<UserRole> authenticate(String login, String password) throws ServiceException;
}
