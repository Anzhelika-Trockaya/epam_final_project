package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

import java.time.LocalDate;
import java.util.Map;

public interface PrescriptionDao {

    Map<Long, Map<String, String>> findAllOfDoctor(long id) throws DaoException;

    Map<Long, Map<String, String>> findAllOfCustomer(long id) throws DaoException;

    boolean updateSoldQuantity(long id, int newQuantity) throws DaoException;

    boolean updateExpirationDateAndSetNeedRenewalFalse(long id, LocalDate newDate) throws DaoException;

    boolean makeNeedRenewal(long id) throws DaoException;

    Map<Long, Map<String, String>> findNeededRenewalOfDoctor(long doctorId) throws DaoException;
}
