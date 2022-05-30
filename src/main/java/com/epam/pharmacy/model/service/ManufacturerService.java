package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    boolean create(String name, String country, String address) throws ServiceException;

    boolean delete(String id) throws ServiceException;

    boolean update(String id, String name, String country, String address) throws ServiceException;

    List<Manufacturer> findAll() throws ServiceException;
}
