package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.Map;

public interface OrderService {

    boolean addToCartWithoutPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    boolean addToCartWithPrescription(long customerId, Map<String, String> positionParams) throws ServiceException;

    Map<Medicine, Integer> findCartContent(long customerId) throws ServiceException;

    int findNumberForPrescriptionInCart(long prescriptionId, long customerId) throws ServiceException;
}
