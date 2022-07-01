package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Prescription;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface MedicineService {
    boolean create(Map<String, String> medicineData) throws ServiceException;

    Optional<Medicine> findById(String medicineIdString) throws ServiceException;

    Map<String, Object> findMedicineContentById(long id) throws ServiceException;

    Map<Long, Map<String, Object>> findAll() throws ServiceException;

    boolean update(Map<String, String> medicineData) throws ServiceException;

    Optional<Integer> findTotalPackages(long id) throws ServiceException;

    Map<Long, Map<String, Object>> findByParams(Map<String, String> paramsMap) throws ServiceException;

    Map<Long, Map<String, Object>> findByPrescription(Prescription prescription) throws ServiceException;

    Map<Long, Map<String, Object>> findAllAvailableForCustomer(long customerId) throws ServiceException;

    Map<Long, Map<String, Object>> findByParamsForCustomer(long customerId, HashMap<String, String> stringStringHashMap) throws ServiceException;
}
