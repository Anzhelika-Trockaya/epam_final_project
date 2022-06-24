package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MedicineService {
    boolean create(Map<String, String> medicineData) throws ServiceException;

    Optional<Medicine> findById(String medicineIdString) throws ServiceException;

    Map<Long, Map<String, Object>> findAll() throws ServiceException;

    boolean update(Map<String, String> medicineData) throws ServiceException;

    Optional<Integer> findTotalPackages(long id) throws ServiceException;

    Map<Long, Map<String, Object>> findByParams(HashMap<String, String> paramsMap) throws ServiceException;
}
