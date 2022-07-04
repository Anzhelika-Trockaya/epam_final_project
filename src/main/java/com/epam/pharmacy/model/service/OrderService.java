package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.UserRole;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface OrderService {

    boolean addToCartWithoutPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    boolean addToCartWithPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    Map<Long, Integer> findMedicineInCartWithQuantity(long customerId) throws ServiceException;

    int findNumberForPrescriptionInCart(long prescriptionId, long customerId) throws ServiceException;

    List<Map<String, Object>> findCartContent(long customerId) throws ServiceException;

    List<Map<String, Object>> findOrderContent(long orderId) throws ServiceException;

    boolean deletePositionFromCart(long medicineId, long prescriptionId, long customerId) throws ServiceException;

    boolean clearCart(long customerId) throws ServiceException;

    boolean changePositionQuantityInCart(long medicineId,
                                         long prescriptionId, int quantity, long customerId) throws ServiceException;

    boolean order(long customerId, BigDecimal expectedTotalCost) throws ServiceException;

    Set<Order> findAllUserOrders(long userId, UserRole role) throws ServiceException;

    Set<Order> findPharmacistOrdersWithState(long userId, Order.State state) throws ServiceException;

    Optional<Order> findNextPaidOrder(long customerId) throws ServiceException;

    int findNewOrdersQuantity() throws ServiceException;

    Optional<Order> findById(long orderId) throws ServiceException;

    boolean completeOrder(long orderId) throws ServiceException;
}
