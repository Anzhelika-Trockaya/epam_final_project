package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.*;
import com.epam.pharmacy.model.dao.impl.*;
import com.epam.pharmacy.model.entity.DosageUnit;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.PropertyKey.*;

public class MedicineServiceImpl implements MedicineService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean create(Map<String, String> medicineData) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectMedicineData(medicineData)) {
            return false;
        }
        Medicine medicine = buildMedicine(medicineData);
        InternationalMedicineNameDaoImpl internationalMedicineNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl medicineFormDao = new MedicineFormDaoImpl();
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(internationalMedicineNameDao, manufacturerDao, medicineFormDao, medicineDao);
            boolean result = true;
            if (!internationalMedicineNameDao.findById(medicine.getInternationalNameId()).isPresent()) {
                medicineData.put(INCORRECT_INTERNATIONAL_NAME, ADDING_MEDICINE_INCORRECT_INTERNATIONAL_NAME);
                result = false;
            }
            if (!manufacturerDao.findById(medicine.getManufacturerId()).isPresent()) {
                medicineData.put(INCORRECT_MANUFACTURER, ADDING_MEDICINE_INCORRECT_MANUFACTURER);
                result = false;
            }
            if (!medicineFormDao.findById(medicine.getFormId()).isPresent()) {
                medicineData.put(INCORRECT_FORM, ADDING_MEDICINE_INCORRECT_FORM);
                result = false;
            }
            if (result) {
                result = medicineDao.create(medicine);
            }
            return result;
        } catch (DaoException e) {
            LOGGER.error("Exception when create medicine" + e);
            throw new ServiceException("Exception when create medicine", e);
        }
    }

    @Override
    public boolean delete(String id) throws ServiceException {
        return false;
    }

    @Override
    public Optional<Medicine> findById(String medicineIdString) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<Medicine> optionalMedicine = Optional.empty();
        if (!validator.isCorrectId(medicineIdString)) {
            LOGGER.error("Exception when checked medicine quantity. Incorrect id=" + medicineIdString);
            return optionalMedicine;
        }
        long idValue;
        try {
            idValue = Long.parseLong(medicineIdString);
        } catch (NumberFormatException e) {
            LOGGER.error("Exception when checked medicine quantity. Incorrect id=" + medicineIdString);
            return optionalMedicine;
        }
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao);
            return medicineDao.findById(idValue);
        } catch (DaoException e) {
            LOGGER.error("Exception when checked medicine quantity." + e);
            throw new ServiceException("Exception when checked medicine quantity.", e);
        }
    }

    @Override
    public List<Medicine> findByName(String medicineName) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isNotEmpty(medicineName)) {
            LOGGER.error("Exception when finding medicines by name. Incorrect name=" + medicineName);
            return new ArrayList<>();
        }
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao);
            return medicineDao.findByName(medicineName.trim().toUpperCase());
        } catch (DaoException e) {
            LOGGER.error("Exception when checked medicine quantity." + e);
            throw new ServiceException("Exception when checked medicine quantity.", e);
        }
    }

    @Override
    public List<Medicine> findAll() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao);
            return medicineDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Exception when find all medicines." + e);
            throw new ServiceException("Exception when find all medicines.", e);
        }
    }

    private Medicine buildMedicine(Map<String, String> medicineData) throws ServiceException {
        String name = medicineData.get(MEDICINE_NAME).trim().toUpperCase();
        String internationalNameIdString = medicineData.get(MEDICINE_INTERNATIONAL_NAME_ID);
        long internationalNameId = Long.parseLong(internationalNameIdString);
        String manufacturerIdString = medicineData.get(MEDICINE_MANUFACTURER_ID);
        long manufacturerId = Long.parseLong(manufacturerIdString);
        String formIdString = medicineData.get(MEDICINE_FORM_ID);
        long formId = Long.parseLong(formIdString);
        String dosageString = medicineData.get(MEDICINE_DOSAGE);
        int dosage = Integer.parseInt(dosageString);
        String dosageUnitString = medicineData.get(MEDICINE_DOSAGE_UNIT);
        DosageUnit dosageUnit = DosageUnit.valueOf(dosageUnitString);
        String needPrescriptionString = medicineData.get(MEDICINE_NEED_PRESCRIPTION);
        boolean needPrescription = Boolean.TRUE.toString().equals(needPrescriptionString);
        String amountInPartString = medicineData.get(MEDICINE_AMOUNT_IN_PART);
        int amountInPart = Integer.parseInt(amountInPartString);
        String partsInPackageString = medicineData.get(MEDICINE_PARTS_IN_PACKAGE);
        int partsInPackage = Integer.parseInt(partsInPackageString);
        String totalPartsString = medicineData.get(MEDICINE_TOTAL_PARTS);
        int totalParts = Integer.parseInt(totalPartsString);
        String priceString = medicineData.get(MEDICINE_PRICE);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceString));
        String ingredients = medicineData.get(MEDICINE_INGREDIENTS);
        String instruction = medicineData.get(MEDICINE_INSTRUCTION);
        String image = medicineData.get(MEDICINE_IMAGE_LINK);
        return new Medicine.Builder().
                buildName(name).
                buildDosage(dosage).
                buildDosageUnit(dosageUnit).
                buildFormId(formId).
                buildAmountInPart(amountInPart).
                buildPartsInPackage(partsInPackage).
                buildTotalNumberOfParts(totalParts).
                buildInternationalNameId(internationalNameId).
                buildManufacturerId(manufacturerId).
                buildPrice(price).
                buildNeedPrescription(needPrescription).
                buildIngredients(ingredients).
                buildInstruction(instruction).
                buildImagePath(image).
                build();
    }
}
