package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Prescription;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MedicineDao {

    List<Medicine> findWithPositiveTotalPackages() throws DaoException;

    List<Medicine> findAvailableForCustomer(long customerId) throws DaoException;

    List<Medicine> findByParams(Map<String, String> paramsMap) throws DaoException;

    List<Medicine> findByParamsAvailableForCustomer(long customerId, HashMap<String, String> paramsMap) throws DaoException;

    List<Medicine> findByPrescription(Prescription prescription) throws DaoException;

    List<Medicine> findByInternationalNameId(long id) throws DaoException;

    List<Medicine> findByManufacturerId(long id) throws DaoException;

    List<Medicine> findByFormId(long id) throws DaoException;

    boolean increaseTotalPackages(long medicineId, int value) throws DaoException;

    Optional<Integer> findTotalPackages(long id) throws DaoException;

    Optional<BigDecimal> findMedicinePrice(long medicineId) throws DaoException;
}
