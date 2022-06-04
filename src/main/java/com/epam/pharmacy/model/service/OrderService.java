package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.Map;

public interface OrderService {
    boolean addToCart(long customerId, String medicineIdString, String quantityString) throws ServiceException;

    Map<Medicine, Integer> findCartContent(long customerId) throws ServiceException;

    Map<Long, Integer> findCartPositions(long customerId) throws ServiceException;
}
