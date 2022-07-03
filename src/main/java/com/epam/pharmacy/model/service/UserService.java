package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;

    boolean create(Map<String, String> userData) throws ServiceException;

    boolean changeState(String id, String state) throws ServiceException;

    boolean updatePassword(long userId, Map<String, String> passwordData) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    List<User> findAllExceptId(long id) throws ServiceException;

    List<User> findAll() throws ServiceException;

    List<User> findActiveCustomers() throws ServiceException;

    Optional<User> updateUserInfo(Map<String, String> userData) throws ServiceException;

    List<User> findByParams(Map<String, String> paramsMap) throws ServiceException;

    BigDecimal findAccountBalance(long customerId) throws ServiceException;

    boolean depositToCustomerAccount(long customerId, String value) throws ServiceException;

    BigDecimal findUserBalance(long id) throws ServiceException;

    String findUserFullName(long id) throws ServiceException;
}
