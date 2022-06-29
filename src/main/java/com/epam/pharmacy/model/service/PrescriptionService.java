package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;

import java.util.Map;
import java.util.Optional;

public interface PrescriptionService {

    boolean renewalForAMonth(String prescriptionId) throws ServiceException;

    Map<Long, Map<String, Object>> findAllByDoctor(long doctorId) throws ServiceException;

    Map<Long, Map<String, Object>> findRenewalRequestsByDoctor(long doctorId) throws ServiceException;

    Map<Long, Map<String, Object>> findAllByCustomer(long customerId) throws ServiceException;

    boolean create(long doctorId, Map<String, String> data) throws ServiceException;

    boolean makeNeedRenewal(String prescriptionId) throws ServiceException;

    Optional<Prescription> findById(String prescriptionId) throws ServiceException;

    Optional<Prescription> findById(long id) throws ServiceException;

    boolean deleteIfIsNotUsed(String prescriptionId) throws ServiceException;
}
