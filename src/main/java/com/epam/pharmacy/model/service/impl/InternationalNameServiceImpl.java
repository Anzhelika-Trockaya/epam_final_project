package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.impl.InternationalMedicineNameDaoImpl;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.service.InternationalNameService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

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
    public Optional<InternationalMedicineName> update(String idString, String name) throws ServiceException {
        DataValidator dataValidator = DataValidatorImpl.getInstance();
        Optional<InternationalMedicineName> nameOptional = Optional.empty();
        if(!dataValidator.isCorrectInternationalName(name) || !dataValidator.isCorrectId(idString)){
            LOGGER.error("Exception when update international name. Incorrect data: id=" + idString+" name="+name);
            return nameOptional;
        }
        long id;
        try{
            id=Long.parseLong(idString);
            LOGGER.error("Exception when update international name. Incorrect data: id=" + idString+" name="+name);
        } catch (NumberFormatException e) {
            return nameOptional;
        }
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(internationalNameDao);
            //fixme check existing name!!!!!
            InternationalMedicineName internationalMedicineName = new InternationalMedicineName(id, name);
            return internationalNameDao.update(internationalMedicineName);
        } catch (DaoException e) {
            LOGGER.error("Exception when update international name. id="+idString+", new name = "+name + e);
            throw new ServiceException("Exception when find all international names. id="+idString+", new name = "+name, e);
        }
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
