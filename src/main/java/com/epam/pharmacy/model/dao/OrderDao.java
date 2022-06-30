package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.OrderPosition;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface OrderDao {
    boolean addPositionToOrder(long orderId, long medicineId, int quantity, long prescriptionId) throws DaoException;

    boolean addPositionToOrder(long orderId, long medicineId, int quantity) throws DaoException;

    Map<Long, Integer> findMedicineIdWithQuantityInOrder(long orderId) throws DaoException;

    Set<OrderPosition> findOrderPositions(long orderId) throws DaoException;

    long getOrderIdWithoutPaymentIfExists(long customerId) throws DaoException;

    boolean createEmptyOrder(long customerId) throws DaoException;

    boolean existsPositionInOrder(long orderId, long medicineId, long prescriptionId) throws DaoException;

    boolean increaseQuantityInOrderPosition(long orderId, long medicineId, int quantity,
                                            long prescriptionId) throws DaoException;

    int findMedicineQuantityInOrder(long orderId, long medicineId) throws DaoException;

    int findMedicineQuantityInOrderExceptCurrentPosition(long orderId, long medicineId,
                                                         long prescriptionId) throws DaoException;

    int findNumberForPrescriptionInOrder(long orderId, long prescriptionId) throws DaoException;

    boolean existsPrescriptionInOrders(long prescriptionId) throws DaoException;

    boolean deletePositionFromOrder(long cartOrderId, long medicineId, long prescriptionId) throws DaoException;

    boolean deleteAllPositionsFromOrder(long orderId) throws DaoException;

    boolean changePositionQuantityInOrder(long orderId, long medicineId, long prescriptionId,
                                          int quantity) throws DaoException;

    int findNumberForPrescriptionInOrderExceptCurrentPosition(long orderId, long medicineId,
                                                              long prescriptionId) throws DaoException;

    boolean updatePriceInPosition(long orderId, long medicineId, long prescriptionId,
                                  BigDecimal price) throws DaoException;

    boolean makeOrderPaidAndUpdateTotalCostAndPaidDate(long cartOrderId, BigDecimal totalCost) throws DaoException;
}
