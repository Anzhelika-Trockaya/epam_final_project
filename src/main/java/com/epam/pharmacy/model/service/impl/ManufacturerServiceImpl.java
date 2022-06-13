package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.ManufacturerDaoImpl;
import com.epam.pharmacy.model.dao.impl.MedicineDaoImpl;
import com.epam.pharmacy.model.entity.Manufacturer;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.service.ManufacturerService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ManufacturerServiceImpl implements ManufacturerService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean create(String name, String country) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        if (!dataValidator.isCorrectManufacturerName(name) || !dataValidator.isCorrectCountry(country)) {
            return false;
        }
        Manufacturer manufacturer = new Manufacturer(name, country);
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(manufacturerDao);
            if (manufacturerDao.findByName(name).isPresent()) {
                return false;
            }
            return manufacturerDao.create(manufacturer);
        } catch (DaoException e) {
            LOGGER.error("Exception when create manufacturer. Manufacturer = " + manufacturer, e);
            throw new ServiceException("Exception when create manufacturer. Manufacturer = " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(String idString) throws ServiceException {
        long id = Long.parseLong(idString);
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, manufacturerDao);
            List<Medicine> medicines = medicineDao.findByManufacturerId(id);
            if (!medicines.isEmpty()) {
                return false;
            }
            return manufacturerDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error("Exception when delete manufacturer. id = " + id, e);
            throw new ServiceException("Exception when delete manufacturer. id = " + id, e);
        }
    }

    @Override
    public Optional<Manufacturer> update(String id, String name, String country) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        if (!dataValidator.isCorrectManufacturerName(name) || !dataValidator.isCorrectCountry(country)) {
            return Optional.empty();
        }
        long idValue = Long.parseLong(id);
        Manufacturer manufacturer = new Manufacturer(idValue, name, country);
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(manufacturerDao);
            Optional<Manufacturer> manufacturerWithName = manufacturerDao.findByName(name);
            if (manufacturerWithName.isPresent() && manufacturerWithName.get().getId() != idValue) {
                return Optional.empty();
            }
            return manufacturerDao.update(manufacturer);
        } catch (DaoException e) {
            LOGGER.error("Exception when update manufacturer. Manufacturer = " + manufacturer, e);
            throw new ServiceException("Exception when update manufacturer. Manufacturer = " + manufacturer, e);
        }
    }

    @Override
    public List<Manufacturer> findAll() throws ServiceException {
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(manufacturerDao);
            return manufacturerDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all manufacturers.", e);
            throw new ServiceException("Exception when find all manufacturers.", e);
        }
    }
}
