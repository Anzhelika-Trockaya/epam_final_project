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

/**
 * The type Medicine service.
 */
class MedicineServiceImpl implements MedicineService {
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
    public Map<String, Object> findMedicineContentById(long id) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao, manufacturerDao, formDao);
            Optional<Medicine> medicineOptional = medicineDao.findById(id);
            if (!medicineOptional.isPresent()) {
                LOGGER.warn("Exception medicine with id=" + id + " not found");
                throw new ServiceException("Exception medicine with id=" + id + " not found");
            }
            return buildMedicineDataMap(medicineOptional.get(), internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when find medicine content. id=" + id, e);
            throw new ServiceException("Exception when find medicine content. id=" + id, e);
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
    public Map<Long, Map<String, Object>> findAllAvailable() throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao, manufacturerDao, formDao);
            List<Medicine> medicines = medicineDao.findWithPositiveTotalPackages();
            return buildMedicinesDataMap(medicines, internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when find all available medicines.", e);
            throw new ServiceException("Exception when find all available medicines.", e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findAllAvailableForCustomer(long customerId) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao, manufacturerDao, formDao);
            List<Medicine> medicines = medicineDao.findAvailableForCustomer(customerId);
            return buildMedicinesDataMap(medicines, internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when find medicines available for customer. customerId=" + customerId, e);
            throw new ServiceException("Exception when find medicines available for customer. customerId=" + customerId, e);
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
                medicineDao.increaseTotalPackages(medicine.getId(), changeTotalValue);
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
    public Map<Long, Map<String, Object>> findByParams(Map<String, String> paramsMap) throws ServiceException {
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

    @Override
    public Map<Long, Map<String, Object>> findByParamsForCustomer(long customerId,
                                                                  HashMap<String, String> paramsMap) throws ServiceException {
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
            List<Medicine> medicines = medicineDao.findByParamsAvailableForCustomer(customerId, paramsMap);
            return buildMedicinesDataMap(medicines, internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when search medicines. customerId=" + customerId + " paramsMap=" + paramsMap, e);
            throw new ServiceException("Exception when search medicines. customerId=" + customerId + " paramsMap=" + paramsMap, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findByPrescription(Prescription prescription) throws ServiceException {
        MedicineDaoImpl medicineDao = new MedicineDaoImpl();
        InternationalMedicineNameDaoImpl internationalNameDao = new InternationalMedicineNameDaoImpl();
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        MedicineFormDaoImpl formDao = new MedicineFormDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginWithAutoCommit(medicineDao, internationalNameDao, manufacturerDao, formDao);
            List<Medicine> medicines = medicineDao.findByPrescription(prescription);
            return buildMedicinesDataMap(medicines, internationalNameDao, manufacturerDao, formDao);
        } catch (DaoException e) {
            LOGGER.error("Exception when search medicines for prescription." + prescription, e);
            throw new ServiceException("Exception when search medicines for prescription." + prescription, e);
        }
    }

    private void replaceEmptyParamsByPercent(Map<String, String> paramsMap) {
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
                                                                 MedicineFormDaoImpl formDao) throws DaoException, ServiceException {
        Map<Long, Map<String, Object>> resultMap = new HashMap<>();
        Map<String, Object> currentMedicineData;
        for (Medicine medicine : medicines) {
            currentMedicineData = buildMedicineDataMap(medicine, internationalNameDao, manufacturerDao, formDao);
            resultMap.put(medicine.getId(), currentMedicineData);
        }
        return resultMap;
    }

    private Map<String, Object> buildMedicineDataMap(Medicine medicine,
                                                     InternationalMedicineNameDaoImpl internationalNameDao,
                                                     ManufacturerDaoImpl manufacturerDao,
                                                     MedicineFormDaoImpl formDao) throws DaoException, ServiceException {
        Map<String, Object> medicineData = new HashMap<>();
        Optional<Manufacturer> manufacturerOptional = manufacturerDao.findById(medicine.getManufacturerId());
        Optional<MedicineForm> formOptional = formDao.findById(medicine.getFormId());
        Optional<InternationalMedicineName> internationalNameOptional =
                internationalNameDao.findById(medicine.getInternationalNameId());
        if (manufacturerOptional.isPresent() && formOptional.isPresent() && internationalNameOptional.isPresent()) {
            medicineData.put(ParameterName.MEDICINE, medicine);
            medicineData.put(ParameterName.MANUFACTURER, manufacturerOptional.get());
            medicineData.put(ParameterName.FORM, formOptional.get());
            medicineData.put(ParameterName.INTERNATIONAL_NAME, internationalNameOptional.get());
        } else {
            LOGGER.warn("Exception when build medicine data=" + medicine);
            throw new ServiceException("Exception when build medicine data=" + medicine);
        }
        return medicineData;
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
                buildImagePath(image).
                build();
    }
}
