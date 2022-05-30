package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.MedicineFormDaoImpl;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.service.MedicineFormService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MedicineFormServiceImpl implements MedicineFormService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean create(String name, String unit) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(String id) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(String id, String name, String unit) throws ServiceException {
        return false;
    }

    @Override
    public List<MedicineForm> findAll() throws ServiceException {
        MedicineFormDaoImpl medicineFormDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineFormDao);
            return medicineFormDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all medicine forms." + e);
            throw new ServiceException("Exception when find all medicine forms.", e);
        }
    }
}
