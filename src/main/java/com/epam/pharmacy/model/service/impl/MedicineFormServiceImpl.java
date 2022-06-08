package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.MedicineDaoImpl;
import com.epam.pharmacy.model.dao.impl.MedicineFormDaoImpl;
import com.epam.pharmacy.model.entity.*;
import com.epam.pharmacy.model.service.MedicineFormService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MedicineFormServiceImpl implements MedicineFormService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean create(String name, String unit) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        if (!dataValidator.isCorrectFormName(name) || !dataValidator.isCorrectFormUnit(unit)) {
            return false;
        }
        FormUnit formUnit = FormUnit.valueOf(unit);
        MedicineForm form = new MedicineForm(name, formUnit);
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(formDao);
            if (formDao.existFormName(name)) {
                return false;
            }
            return formDao.create(form);
        } catch (DaoException e) {
            LOGGER.error("Exception when create form. Form = " + form + e);
            throw new ServiceException("Exception when create form. Form = " + form, e);
        }
    }

    @Override
    public boolean delete(String idString) throws ServiceException {
        long id;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when delete form. Incorrect id=" + idString);
            return false;
        }
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, formDao);
            List<Medicine> medicines = medicineDao.findByFormId(id);
            if (!medicines.isEmpty()) {
                return false;
            }
            return formDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error("Exception when delete form. id = " + id + e);
            throw new ServiceException("Exception when delete form. id = " + id, e);
        }
    }

    @Override
    public Optional<MedicineForm> update(String id, String name, String unit) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        if (!dataValidator.isCorrectFormName(name) || !dataValidator.isCorrectFormUnit(unit)) {
            LOGGER.error("Exception when update form. Incorrect data: id=" + id + " name=" + name + " unit=" + unit);
            return Optional.empty();
        }
        FormUnit formUnit = FormUnit.valueOf(unit);
        long idValue;
        try {
            idValue = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when update form. Incorrect id=" + id);
            return Optional.empty();
        }
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        MedicineForm form = new MedicineForm(idValue, name, formUnit);
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(formDao);
            if (formDao.existFormName(name)) {
                return Optional.empty();
            }
            return formDao.update(form);
        } catch (DaoException e) {
            LOGGER.error("Exception when update form." + form + e);
            throw new ServiceException("Exception when update form. " + form, e);
        }
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
