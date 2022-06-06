package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MedicineService {
    boolean create(Map<String, String> medicineData) throws ServiceException;

    boolean delete(String id) throws ServiceException;

   //fixme boolean update(String id, String name, String unit) throws ServiceException;

    Optional<Medicine> findById(String medicineIdString) throws ServiceException;

    //FIXME!!!!
    List<Medicine> findByName(String medicineName) throws ServiceException;

    List<Medicine> findAll() throws ServiceException;
}
