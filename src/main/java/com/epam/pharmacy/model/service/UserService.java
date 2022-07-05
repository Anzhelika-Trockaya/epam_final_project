package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Authenticates user.
     *
     * @param login    the login
     * @param password the password
     * @return the optional user. Returns empty optional if incorrect login or password
     * @throws ServiceException the service exception
     */
    Optional<User> authenticate(String login, String password) throws ServiceException;

    /**
     * Creates new user.
     *
     * @param userData the user data
     * @return the boolean {@code true} if created
     * @throws ServiceException the service exception
     */
    boolean create(Map<String, String> userData) throws ServiceException;

    /**
     * Changes user state.
     *
     * @param id    the id
     * @param state the state
     * @return the boolean {@code true} if changed
     * @throws ServiceException the service exception
     */
    boolean changeState(String id, String state) throws ServiceException;

    /**
     * Updates user password.
     *
     * @param userId       the user id
     * @param passwordData the password data
     * @return the boolean {@code true} if updated
     * @throws ServiceException the service exception
     */
    boolean updatePassword(long userId, Map<String, String> passwordData) throws ServiceException;

    /**
     * Finds user by id.
     *
     * @param id the id
     * @return the optional user
     * @throws ServiceException the service exception
     */
    Optional<User> findById(long id) throws ServiceException;

    /**
     * Finds all users list except user with id.
     *
     * @param id the id
     * @return the list of users
     * @throws ServiceException the service exception
     */
    List<User> findAllExceptId(long id) throws ServiceException;

    /**
     * Finds all users list.
     *
     * @return the users list
     * @throws ServiceException the service exception
     */
    List<User> findAll() throws ServiceException;

    /**
     * Finds customers with active state list.
     *
     * @return the list of active customers
     * @throws ServiceException the service exception
     */
    List<User> findActiveCustomers() throws ServiceException;

    /**
     * Updates user info.
     *
     * @param userData the user data
     * @return the optional user with old data. Returns empty optional if not updated
     * @throws ServiceException the service exception
     */
    Optional<User> updateUserInfo(Map<String, String> userData) throws ServiceException;

    /**
     * Finds users by params.
     *
     * @param paramsMap the params map
     * @return the users list
     * @throws ServiceException the service exception
     */
    List<User> findByParams(Map<String, String> paramsMap) throws ServiceException;

    /**
     * Finds user account balance.
     *
     * @param customerId the customer id
     * @return the balance
     * @throws ServiceException the service exception
     */
    BigDecimal findUserAccountBalance(long customerId) throws ServiceException;

    /**
     * Deposits to customer account.
     *
     * @param customerId the customer id
     * @param value      the value
     * @return the boolean {@code true} if deposit was successful
     * @throws ServiceException the service exception
     */
    boolean depositToCustomerAccount(long customerId, String value) throws ServiceException;

    /**
     * Finds user full name string.
     *
     * @param id the id
     * @return the full name string
     * @throws ServiceException the service exception
     */
    String findUserFullName(long id) throws ServiceException;
}
