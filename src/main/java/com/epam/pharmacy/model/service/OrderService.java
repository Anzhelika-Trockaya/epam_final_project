package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.OrderPosition;

import java.util.Map;
import java.util.Set;

public interface OrderService {

    boolean addToCartWithoutPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    boolean addToCartWithPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    Map<Long, Integer> findMedicineInCartWithQuantity(long customerId) throws ServiceException;

    int findNumberForPrescriptionInCart(long prescriptionId, long customerId) throws ServiceException;

    Set<OrderPosition> findCartPositions(long customerId) throws ServiceException;

    boolean deletePositionFromCart(long medicineId, long prescriptionId, long customerId) throws ServiceException;

    boolean clearCart(long customerId) throws ServiceException;
}
