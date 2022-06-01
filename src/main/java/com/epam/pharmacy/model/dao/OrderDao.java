package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

public interface OrderDao {
    long getOrderIdWithoutPayment(long customerId) throws DaoException;

    boolean createEmptyOrder(long customerId) throws DaoException;
}
