package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<UserRole> authenticate(String login, String password) throws ServiceException;
    boolean create(Map<String, String> userData) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
}
