package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.UserRole;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Order service.
 */
public interface OrderService {

    /**
     * Adds position without prescription to cart.
     *
     * @param customerId     the customer id
     * @param positionParams the position params
     * @return the boolean {@code true} if added
     * @throws ServiceException the service exception
     */
    boolean addToCartWithoutPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    /**
     * Adds position with prescription to cart boolean.
     *
     * @param customerId     the customer id
     * @param positionParams the position params
     * @return the boolean {@code true} if added
     * @throws ServiceException the service exception
     */
    boolean addToCartWithPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    /**
     * Finds medicine with quantity map in cart.
     *
     * @param customerId the customer id
     * @return the map with medicines id and medicines quantity
     * @throws ServiceException the service exception
     */
    Map<Long, Integer> findMedicineInCartWithQuantity(long customerId) throws ServiceException;

    /**
     * Finds number for prescription in cart.
     *
     * @param prescriptionId the prescription id
     * @param customerId     the customer id
     * @return the number for prescription in the cart
     * @throws ServiceException the service exception
     */
    int findNumberForPrescriptionInCart(long prescriptionId, long customerId) throws ServiceException;

    /**
     * Finds cart content list.
     *
     * @param customerId the customer id
     * @return the list of cart positions data
     * @throws ServiceException the service exception
     */
    List<Map<String, Object>> findCartContent(long customerId) throws ServiceException;

    /**
     * Finds order content list.
     *
     * @param orderId the order id
     * @return the list of order positions data
     * @throws ServiceException the service exception
     */
    List<Map<String, Object>> findOrderContent(long orderId) throws ServiceException;

    /**
     * Deletes position from cart.
     *
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @param customerId     the customer id
     * @return the boolean {@code true} if deleted
     * @throws ServiceException the service exception
     */
    boolean deletePositionFromCart(long medicineId, long prescriptionId, long customerId) throws ServiceException;

    /**
     * Clears cart.
     *
     * @param customerId the customer id
     * @return the boolean {@code true} if cleared
     * @throws ServiceException the service exception
     */
    boolean clearCart(long customerId) throws ServiceException;

    /**
     * Changes position quantity in cart.
     *
     * @param medicineId     the medicine id
     * @param prescriptionId the prescription id
     * @param quantity       the quantity
     * @param customerId     the customer id
     * @return the boolean {@code true} if changed
     * @throws ServiceException the service exception
     */
    boolean changePositionQuantityInCart(long medicineId,
                                         long prescriptionId, int quantity, long customerId) throws ServiceException;

    /**
     * Makes order.
     *
     * @param customerId        the customer id
     * @param expectedTotalCost the expected total cost
     * @return the boolean {@code true} if made
     * @throws ServiceException the service exception
     */
    boolean order(long customerId, BigDecimal expectedTotalCost) throws ServiceException;

    /**
     * Finds all user orders.
     *
     * @param userId the user id
     * @param role   the role
     * @return the set of user orders
     * @throws ServiceException the service exception
     */
    Set<Order> findAllUserOrders(long userId, UserRole role) throws ServiceException;

    /**
     * Finds pharmacist with state orders.
     *
     * @param userId the user id
     * @param state  the state
     * @return the set of pharmacist with state orders
     * @throws ServiceException the service exception
     */
    Set<Order> findPharmacistOrdersWithState(long userId, Order.State state) throws ServiceException;

    /**
     * Finds next paid order.
     *
     * @param customerId the customer id
     * @return the order optional.
     * @throws ServiceException the service exception
     */
    Optional<Order> findNextPaidOrder(long customerId) throws ServiceException;

    /**
     * Finds paid orders quantity.
     *
     * @return the paid orders quantity
     * @throws ServiceException the service exception
     */
    int findPaidOrdersQuantity() throws ServiceException;

    /**
     * Finds order by id optional.
     *
     * @param orderId the order id
     * @return the order optional
     * @throws ServiceException the service exception
     */
    Optional<Order> findById(long orderId) throws ServiceException;

    /**
     * Completes order.
     *
     * @param orderId the order id
     * @return the boolean {@code true} if completed
     * @throws ServiceException the service exception
     */
    boolean completeOrder(long orderId) throws ServiceException;
}
