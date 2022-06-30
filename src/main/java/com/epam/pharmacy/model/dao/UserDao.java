package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String login, String password) throws DaoException;

    Optional<User> findByLogin(String s) throws DaoException;

    boolean updateState(long id, User.State state) throws DaoException;

    boolean updatePassword(long id, String password) throws DaoException;

    List<User> findActiveCustomers() throws DaoException;

    BigDecimal findAccountBalance(long id) throws DaoException;

    boolean increaseAccountBalance(long id, BigDecimal value) throws DaoException;

    boolean decreaseAccountBalance(long id, BigDecimal totalCost) throws DaoException;
}
