package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

import java.time.LocalDate;
import java.util.Map;

public interface PrescriptionDao {

    Map<Long, Map<String, Object>> findAllOfDoctor(long id) throws DaoException;

    Map<Long, Map<String, Object>> findAllOfCustomer(long id) throws DaoException;

    boolean updateExpirationDateAndSetNeedRenewalFalse(long id, LocalDate newDate) throws DaoException;

    boolean makeNeedRenewal(long id) throws DaoException;

    Map<Long, Map<String, Object>> findNeededRenewalOfDoctor(long doctorId) throws DaoException;

    int findPrescriptionAvailableNumber(long prescriptionId) throws DaoException;

    boolean increaseSoldQuantity(long prescriptionId, long medicineId, int medicinePackages) throws DaoException;
}
