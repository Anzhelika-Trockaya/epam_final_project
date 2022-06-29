package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.FormUnit;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Prescription;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PrescriptionDao {

    Map<Long, Map<String, Object>> findAllOfDoctor(long id) throws DaoException;

    Map<Long, Map<String, Object>> findAllOfCustomer(long id) throws DaoException;

    boolean updateSoldQuantity(long id, int newQuantity) throws DaoException;

    boolean updateExpirationDateAndSetNeedRenewalFalse(long id, LocalDate newDate) throws DaoException;

    boolean makeNeedRenewal(long id) throws DaoException;

    Map<Long, Map<String, Object>> findNeededRenewalOfDoctor(long doctorId) throws DaoException;

    int findPrescriptionAvailableNumber(long prescriptionId) throws DaoException;
}
