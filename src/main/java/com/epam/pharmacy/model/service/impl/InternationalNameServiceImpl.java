package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.InternationalMedicineNameDao;
import com.epam.pharmacy.model.dao.impl.InternationalMedicineNameDaoImpl;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.service.InternationalNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class InternationalNameServiceImpl implements InternationalNameService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean create(String name) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(String id) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(String id, String name) throws ServiceException {
        return false;
    }

    @Override
    public List<InternationalMedicineName> findAll() throws ServiceException {
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(internationalNameDao);
            return internationalNameDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all international names." + e);
            throw new ServiceException("Exception when find all international names.", e);
        }
    }
}
