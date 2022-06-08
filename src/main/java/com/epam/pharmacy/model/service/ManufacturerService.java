package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    boolean create(String name, String country) throws ServiceException;

    boolean delete(String id) throws ServiceException;

    Optional<Manufacturer> update(String id, String name, String country) throws ServiceException;

    List<Manufacturer> findAll() throws ServiceException;
}
