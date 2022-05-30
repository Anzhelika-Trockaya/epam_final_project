package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.InternationalMedicineNameDaoImpl;
import com.epam.pharmacy.model.dao.impl.ManufacturerDaoImpl;
import com.epam.pharmacy.model.entity.Manufacturer;
import com.epam.pharmacy.model.service.ManufacturerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ManufacturerServiceImpl implements ManufacturerService {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public boolean create(String name, String country, String address) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(String id) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(String id, String name, String country, String address) throws ServiceException {
        return false;
    }

    @Override
    public List<Manufacturer> findAll() throws ServiceException {
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(manufacturerDao);
            return manufacturerDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all manufacturers." + e);
            throw new ServiceException("Exception when find all manufacturers.", e);
        }
    }
}
