package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao {
    /**
     * Authenticates user.
     *
     * @param login    the login
     * @param password the password
     * @return the optional user. Return empty optional if is failed
     * @throws DaoException the dao exception
     */
    Optional<User> authenticate(String login, String password) throws DaoException;

    /**
     * Finds user by login.
     *
     * @param login the login
     * @return the optional user.
     * @throws DaoException the dao exception
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Updates user state.
     *
     * @param id    the id
     * @param state the state
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean updateState(long id, User.State state) throws DaoException;

    /**
     * Updates user password.
     *
     * @param id       the id
     * @param password the password
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean updatePassword(long id, String password) throws DaoException;

    /**
     * Finds active customers list.
     *
     * @return the list of customers with active state
     * @throws DaoException the dao exception
     */
    List<User> findActiveCustomers() throws DaoException;

    /**
     * Finds user account balance.
     *
     * @param id the id
     * @return the balance
     * @throws DaoException the dao exception
     */
    BigDecimal findAccountBalance(long id) throws DaoException;

    /**
     * Increases account balance boolean.
     *
     * @param id    the id
     * @param value the value
     * @return the boolean {@code true} if increased
     * @throws DaoException the dao exception
     */
    boolean increaseAccountBalance(long id, BigDecimal value) throws DaoException;

    /**
     * Decreases account balance boolean.
     *
     * @param id        the id
     * @param totalCost the total cost
     * @return the boolean {@code true} if decreased
     * @throws DaoException the dao exception
     */
    boolean decreaseAccountBalance(long id, BigDecimal totalCost) throws DaoException;
}
