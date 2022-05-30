package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;

import java.util.List;

public interface InternationalNameService {
    boolean create(String name) throws ServiceException;

    boolean delete(String id) throws ServiceException;

    boolean update(String id, String name) throws ServiceException;

    List<InternationalMedicineName> findAll() throws ServiceException;
}
