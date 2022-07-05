package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.OrderPosition;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * The interface Order dao.
 */
public interface OrderDao {
    /**
     * Adds position with prescription id to order.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param quantity       the quantity
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if added
     * @throws DaoException the dao exception
     */
    boolean addPositionWithPrescriptionIdToOrder(long orderId, long medicineId, int quantity, long prescriptionId) throws DaoException;

    /**
     * Adds position without prescription id to order.
     *
     * @param orderId    the order id
     * @param medicineId the medicine id
     * @param quantity   the quantity
     * @return the boolean {@code true} if added
     * @throws DaoException the dao exception
     */
    boolean addPositionWithoutPrescriptionIdToOrder(long orderId, long medicineId, int quantity) throws DaoException;

    /**
     * Finds medicine id with quantity in order map.
     *
     * @param orderId the order id
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<Long, Integer> findMedicineIdWithQuantityInOrder(long orderId) throws DaoException;

    /**
     * Finds next paid order id.
     *
     * @return the order id
     * @throws DaoException the dao exception
     */
    long findNextPaidOrderId() throws DaoException;


    /**
     * Updates order state in order.
     *
     * @param orderId the order id
     * @param state   the state
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean updateOrderStateByOrderId(long orderId, Order.State state) throws DaoException;

    /**
     * Updates pharmacist id in order.
     *
     * @param orderId      the order id
     * @param pharmacistId the pharmacist id
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean updateOrderPharmacistId(long orderId, long pharmacistId) throws DaoException;

    /**
     * Finds order positions set.
     *
     * @param orderId the order id
     * @return the set of orders positions
     * @throws DaoException the dao exception
     */
    Set<OrderPosition> findOrderPositions(long orderId) throws DaoException;

    /**
     * Gets order id without payment if exists.
     *
     * @param customerId the customer id
     * @return the order id without payment if exists
     * @throws DaoException the dao exception
     */
    long getOrderIdWithoutPaymentIfExists(long customerId) throws DaoException;

    /**
     * Creates empty order.
     *
     * @param customerId the customer id
     * @return the boolean {@code true} if created
     * @throws DaoException the dao exception
     */
    boolean createEmptyOrder(long customerId) throws DaoException;

    /**
     * Checks if position exists in order.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if exists
     * @throws DaoException the dao exception
     */
    boolean existsPositionInOrder(long orderId, long medicineId, long prescriptionId) throws DaoException;

    /**
     * Increases quantity in order position.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param quantity       the quantity
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if increased
     * @throws DaoException the dao exception
     */
    boolean increaseQuantityInOrderPosition(long orderId, long medicineId, int quantity,
                                            long prescriptionId) throws DaoException;

    /**
     * Finds medicine quantity in order.
     *
     * @param orderId    the order id
     * @param medicineId the medicine id
     * @return the medicine quantity in order
     * @throws DaoException the dao exception
     */
    int findMedicineQuantityInOrder(long orderId, long medicineId) throws DaoException;

    /**
     * Finds medicine quantity in order except current position.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @return the medicine quantity in order except current position
     * @throws DaoException the dao exception
     */
    int findMedicineQuantityInOrderExceptCurrentPosition(long orderId, long medicineId,
                                                         long prescriptionId) throws DaoException;

    /**
     * Finds number for prescription in order positions.
     *
     * @param orderId        the order id
     * @param prescriptionId the prescription id
     * @return the number for prescription in order positions.
     * @throws DaoException the dao exception
     */
    int findNumberForPrescriptionInOrder(long orderId, long prescriptionId) throws DaoException;

    /**
     * Checks if prescription id exists in orders positions.
     *
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if exists
     * @throws DaoException the dao exception
     */
    boolean existsPrescriptionInOrders(long prescriptionId) throws DaoException;

    /**
     * Deletes position from order.
     *
     * @param cartOrderId    the cart order id
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if deleted
     * @throws DaoException the dao exception
     */
    boolean deletePositionFromOrder(long cartOrderId, long medicineId, long prescriptionId) throws DaoException;

    /**
     * Delete all positions from order.
     *
     * @param orderId the order id
     * @return the boolean {@code true} if deleted
     * @throws DaoException the dao exception
     */
    boolean deleteAllPositionsFromOrder(long orderId) throws DaoException;

    /**
     * Changes position quantity in order.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @param quantity       the quantity
     * @return the boolean {@code true} if changed
     * @throws DaoException the dao exception
     */
    boolean changePositionQuantityInOrder(long orderId, long medicineId, long prescriptionId,
                                          int quantity) throws DaoException;

    /**
     * Finds number for prescription in order except current position.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @return the number for prescription in order except current position.
     * @throws DaoException the dao exception
     */
    int findNumberForPrescriptionInOrderExceptCurrentPosition(long orderId, long medicineId,
                                                              long prescriptionId) throws DaoException;

    /**
     * Updates price in position.
     *
     * @param orderId        the order id
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @param price          the price
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean updatePriceInPosition(long orderId, long medicineId, long prescriptionId,
                                  BigDecimal price) throws DaoException;

    /**
     * Makes order paid and update total cost and paid date.
     *
     * @param cartOrderId the cart order id
     * @param totalCost   the total cost
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean makeOrderPaidAndUpdateTotalCostAndPaidDate(long cartOrderId, BigDecimal totalCost) throws DaoException;

    /**
     * Finds all orders with customer id.
     *
     * @param id the id
     * @return the set of orders with customer id
     * @throws DaoException the dao exception
     */
    Set<Order> findAllWithCustomerId(long id) throws DaoException;

    /**
     * Find all orders with pharmacist id.
     *
     * @param id the id
     * @return the set of orders with pharmacist id
     * @throws DaoException the dao exception
     */
    Set<Order> findAllWithPharmacistId(long id) throws DaoException;

    /**
     * Finds all orders with pharmacist id and with state.
     *
     * @param id    the id
     * @param state the state
     * @return the set of orders with pharmacist id and with state.
     * @throws DaoException the dao exception
     */
    Set<Order> findAllWithPharmacistIdAndState(long id, Order.State state) throws DaoException;

    /**
     * Finds paid orders quantity.
     *
     * @return the paid orders quantity
     * @throws DaoException the dao exception
     */
    int findPaidOrdersQuantity() throws DaoException;
}
