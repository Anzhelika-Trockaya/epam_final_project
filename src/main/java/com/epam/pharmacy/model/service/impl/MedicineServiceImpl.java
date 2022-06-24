package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.*;
import com.epam.pharmacy.model.dao.impl.*;
import com.epam.pharmacy.model.entity.*;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.validator.DataValidator;
import com.epam.pharmacy.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_ID;

public class MedicineServiceImpl implements MedicineService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MAX_TOTAL_VALUE = 999_999_999;
    private static final String PERCENT = "%";

    @Override
    public boolean create(Map<String, String> data) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectMedicineData(data)) {
            return false;
        }
        Medicine medicine = buildMedicine(data);
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao);
            return medicineDao.create(medicine);
        } catch (DaoException e) {
            LOGGER.error("Exception when create medicine", e);
            throw new ServiceException("Exception when create medicine", e);
        }
    }

    @Override
    public Optional<Medicine> findById(String medicineIdString) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        long idValue = Long.parseLong(medicineIdString);
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao);
            return medicineDao.findById(idValue);
        } catch (DaoException e) {
            LOGGER.error("Exception when find medicines by id=" + medicineIdString, e);
            throw new ServiceException("Exception when find medicines by id=" + medicineIdString, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findAll() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao, manufacturerDao, formDao);
            List<Medicine> medicines = medicineDao.findAll();
            return buildMedicinesDataMap(medicines, internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when find all medicines.", e);
            throw new ServiceException("Exception when find all medicines.", e);
        }
    }

    @Override
    public boolean update(Map<String, String> data) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectMedicineData(data)) {
            return false;
        }
        Medicine medicine = buildMedicine(data);
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.begin(medicineDao);
            int changeTotalValue = Integer.parseInt(data.get(MEDICINE_CHANGE_TOTAL_VALUE));
            if (changeTotalValue != 0) {
                Optional<Integer> totalFromDbOptional = medicineDao.findTotalPackages(medicine.getId());
                if (!totalFromDbOptional.isPresent()) {
                    LOGGER.warn("Not found total of medicine with id=" + medicine.getId());
                    return false;
                }
                int totalFromDb = totalFromDbOptional.get();
                if (!isCorrectChangeTotalValue(changeTotalValue, totalFromDb)) {
                    data.put(INCORRECT_CHANGE_TOTAL_VALUE, PropertyKey.EDIT_MEDICINE_INCORRECT_CHANGE_TOTAL_VALUE);
                }
            }
            try {
                medicineDao.updateTotalPackages(medicine.getId(), changeTotalValue);
                Optional<Medicine> optionalMedicine = medicineDao.update(medicine);
                transaction.commit();
                return optionalMedicine.isPresent();
            } catch (DaoException e) {
                LOGGER.warn("Exception when update medicine", e);
                transaction.rollback();
                return false;
            }
        } catch (DaoException e) {
            LOGGER.error("Exception when update medicine", e);
            throw new ServiceException("Exception when update medicine", e);
        }
    }

    @Override
    public Optional<Integer> findTotalPackages(long id) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao);
            return medicineDao.findTotalPackages(id);
        } catch (DaoException e) {
            LOGGER.error("Exception when find total packages of medicine. id=" + id, e);
            throw new ServiceException("Exception when find total packages of medicine. id=" + id, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findByParams(HashMap<String, String> paramsMap) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isCorrectMedicineSearchParamsMap(paramsMap)) {
            return new HashMap<>();
        }
        replaceEmptyParamsByPercent(paramsMap);
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao, manufacturerDao, formDao);
            List<Medicine> medicines = medicineDao.findByParams(paramsMap);
            return buildMedicinesDataMap(medicines, internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when search medicines. paramsMap=" + paramsMap, e);
            throw new ServiceException("Exception when search medicines. paramsMap=" + paramsMap, e);
        }
    }

    private void replaceEmptyParamsByPercent(HashMap<String, String> paramsMap) {
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                entry.setValue(PERCENT);
            }
        }
    }

    private boolean isCorrectChangeTotalValue(int changeTotalValue, int totalFromDb) {
        return !((changeTotalValue < 0) && (-changeTotalValue > totalFromDb)) &&
                !(changeTotalValue > MAX_TOTAL_VALUE - totalFromDb);
    }

    private Map<Long, Map<String, Object>> buildMedicinesDataMap(List<Medicine> medicines,
                                                                 InternationalMedicineNameDaoImpl internationalNameDao,
                                                                 ManufacturerDaoImpl manufacturerDao,
                                                                 MedicineFormDaoImpl formDao) throws DaoException {
        Map<Long, Map<String, Object>> resultMap = new HashMap<>();
        Optional<Manufacturer> currentManufacturerOptional;
        Optional<MedicineForm> currentFormOptional;
        Optional<InternationalMedicineName> currentInternationalNameOptional;
        Map<String, Object> currentMedicineData;
        for (Medicine medicine : medicines) {
            currentManufacturerOptional = manufacturerDao.findById(medicine.getManufacturerId());
            currentFormOptional = formDao.findById(medicine.getFormId());
            currentInternationalNameOptional = internationalNameDao.findById(medicine.getInternationalNameId());
            if (currentManufacturerOptional.isPresent() &&
                    currentFormOptional.isPresent() &&
                    currentInternationalNameOptional.isPresent()) {
                currentMedicineData = new HashMap<>();
                currentMedicineData.put(ParameterName.MEDICINE, medicine);
                currentMedicineData.put(ParameterName.MANUFACTURER, currentManufacturerOptional.get());
                currentMedicineData.put(ParameterName.FORM, currentFormOptional.get());
                currentMedicineData.put(ParameterName.INTERNATIONAL_NAME, currentInternationalNameOptional.get());
            } else {
                LOGGER.warn("Exception when build medicine data=" + medicine);
                continue;
            }
            resultMap.put(medicine.getId(), currentMedicineData);
        }
        return resultMap;
    }

    private Medicine buildMedicine(Map<String, String> medicineData) {
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
        String numberInPackageString = medicineData.get(MEDICINE_NUMBER_IN_PACKAGE);
        int numberInPackage = Integer.parseInt(numberInPackageString);
        String priceString = medicineData.get(MEDICINE_PRICE);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceString));
        String ingredients = medicineData.get(MEDICINE_INGREDIENTS);
        String instruction = medicineData.get(MEDICINE_INSTRUCTION);
        String image = medicineData.get(MEDICINE_IMAGE_LINK);
        Medicine.Builder medicineBuilder;
        String idString = medicineData.get(MEDICINE_ID);
        if (idString != null) {
            long id = Long.parseLong(idString);
            medicineBuilder = new Medicine.Builder(id);
        } else {
            medicineBuilder = new Medicine.Builder();
        }
        if (medicineData.get(MEDICINE_CHANGE_TOTAL_VALUE) == null) {
            String totalPackagesString = medicineData.get(MEDICINE_TOTAL_PACKAGES);
            int totalPackages = Integer.parseInt(totalPackagesString);
            medicineBuilder.buildTotalPackages(totalPackages);
        }
        return medicineBuilder.
                buildName(name).
                buildDosage(dosage).
                buildDosageUnit(dosageUnit).
                buildFormId(formId).
                buildNumberInPackage(numberInPackage).
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
