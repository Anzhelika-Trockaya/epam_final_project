package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;

import java.util.Map;

public interface PrescriptionService {

    boolean renewalForAMonth(String prescriptionId) throws ServiceException;

    Map<Long, Map<String, String>> findAllByDoctor(long doctorId) throws ServiceException;

    Map<Long, Map<String, String>> findRenewalRequestsByDoctor(long doctorId) throws ServiceException;

    Map<Long, Map<String, String>> findAllByCustomer(long customerId) throws ServiceException;

    boolean create(long doctorId, Map<String, String> data) throws ServiceException;

    boolean makeNeedRenewal(String prescriptionId) throws ServiceException;
}
