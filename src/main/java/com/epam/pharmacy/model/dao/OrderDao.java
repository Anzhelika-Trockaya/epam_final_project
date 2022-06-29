package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.OrderPosition;

import java.util.Map;
import java.util.Set;

public interface OrderDao {
    boolean addToOrder(long orderId, long medicineId, int quantity, long prescriptionId) throws DaoException;

    boolean addToOrder(long orderId, long medicineId, int quantity) throws DaoException;

    Map<Long, Integer> findMedicineIdWithQuantityInOrder(long orderId) throws DaoException;

    Set<OrderPosition> findOrderPositions(long orderId) throws DaoException;

    long getOrderIdWithoutPayment(long customerId) throws DaoException;

    boolean createEmptyOrder(long customerId) throws DaoException;

    boolean existsPositionInOrder(long orderId, long medicineId, long prescriptionId) throws DaoException;

    boolean increaseMedicineQuantityInOrderPosition(long orderId, long medicineId, int quantity, long prescriptionId) throws DaoException;

    int findPositionNumberInOrder(long orderId, long medicineId, long prescriptionId) throws DaoException;

    int findNumberForPrescriptionInOrder(long orderId, long prescriptionId) throws DaoException;

    boolean existsPrescriptionInOrders(long prescriptionId) throws DaoException;

    boolean deletePositionFromOrder(long cartOrderId, long medicineId, long prescriptionId) throws DaoException;

    boolean deleteAllPositionsFromOrder(long orderId) throws DaoException;
}
