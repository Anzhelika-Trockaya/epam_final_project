package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;

    boolean create(Map<String, String> userData) throws ServiceException;

    boolean deleteById(String id) throws ServiceException;

    boolean changeState(String id, String state) throws ServiceException;

    boolean updatePassword(String idString, String password) throws ServiceException;

    Optional<User> findById(String id) throws ServiceException;

    List<User> findAllExceptId(long id) throws ServiceException;

    List<User> findAll() throws ServiceException;

    List<User> findActiveCustomers() throws ServiceException;

    Optional<User> updateUserInfo(Map<String, String> userData) throws ServiceException;

    List<User> findByParams(Map<String, String> paramsMap) throws ServiceException;
}
