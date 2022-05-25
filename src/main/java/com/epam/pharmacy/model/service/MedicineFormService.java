package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.MedicineForm;

import java.util.List;

public interface MedicineFormService {
    boolean create(String name, String unit) throws ServiceException;

    boolean delete(String id) throws ServiceException;

    boolean update(String id, String name, String unit) throws ServiceException;

    List<MedicineForm> findAll() throws ServiceException;
}