package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.InternationalMedicineNameDaoImpl;
import com.epam.pharmacy.model.dao.impl.MedicineDaoImpl;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.service.InternationalNameService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The type International name service.
 */
class InternationalNameServiceImpl implements InternationalNameService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean create(String name) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        if (!dataValidator.isCorrectInternationalName(name)) {
            return false;
        }
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(internationalNameDao);
            if (internationalNameDao.existInternationalName(name)) {
                return false;
            }
            InternationalMedicineName internationalMedicineName = new InternationalMedicineName(name);
            return internationalNameDao.create(internationalMedicineName);
        } catch (DaoException e) {
            LOGGER.error("Exception when create international name. Name = " + name, e);
            throw new ServiceException("Exception when create international name. Name = " + name, e);
        }
    }

    @Override
    public boolean delete(String idString) throws ServiceException {
        long id = Long.parseLong(idString);
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao);
            List<Medicine> medicinesWithInternationalName = medicineDao.findByInternationalNameId(id);
            if(!medicinesWithInternationalName.isEmpty()){
                return false;
            }
            return internationalNameDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error("Exception when delete international name. id = " + id, e);
            throw new ServiceException("Exception when delete international name. id = " + id, e);
        }
    }

    @Override
    public Optional<InternationalMedicineName> update(String idString, String name) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        if (!dataValidator.isCorrectInternationalName(name)) {
            return Optional.empty();
        }
        long id = Long.parseLong(idString);
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(internationalNameDao);
            if (internationalNameDao.existInternationalName(name)) {
                return Optional.empty();
            }
            InternationalMedicineName internationalMedicineName = new InternationalMedicineName(id, name);
            return internationalNameDao.update(internationalMedicineName);
        } catch (DaoException e) {
            LOGGER.error("Exception when update international name. id=" + idString + ", new name = " + name, e);
            throw new ServiceException("Exception when update international name. id=" + idString + ", new name = " +
                    name, e);
        }
    }

    @Override
    public List<InternationalMedicineName> findAll() throws ServiceException {
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(internationalNameDao);
            return internationalNameDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all international names.", e);
            throw new ServiceException("Exception when find all international names.", e);
        }
    }
}
