package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.Map;

public interface OrderDao {
    boolean updateQuantityInOrderPosition(long orderId, long medicineId, int quantity) throws DaoException;

    boolean addToCart(long orderId, long medicineId, int quantity) throws DaoException;

    Map<Long, Integer> findOrderMedicineIdAndQuantity(long orderId) throws DaoException;

    Map<Medicine, Integer> findOrderPositions(long orderId) throws DaoException;

    long getOrderIdWithoutPayment(long customerId) throws DaoException;

    boolean createEmptyOrder(long customerId) throws DaoException;

    boolean existsPositionInCart(long orderId, long medicineId) throws DaoException;

    boolean increaseMedicineQuantityInOrder(long orderId, long medicineId, int quantity) throws DaoException;
}
