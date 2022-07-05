package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

import java.time.LocalDate;
import java.util.Map;

/**
 * The interface Prescription dao.
 */
public interface PrescriptionDao {

    /**
     * Finds all doctors prescriptions data map.
     *
     * @param id the id
     * @return the data map of doctors prescriptions
     * @throws DaoException the dao exception
     */
    Map<Long, Map<String, Object>> findAllOfDoctor(long id) throws DaoException;

    /**
     * Finds all customers prescriptions data map.
     *
     * @param id the id
     * @return the data map of customers prescriptions
     * @throws DaoException the dao exception
     */
    Map<Long, Map<String, Object>> findAllOfCustomer(long id) throws DaoException;

    /**
     * Updates expiration date and sets need renewal false.
     *
     * @param id      the id
     * @param newDate the new date
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean updateExpirationDateAndSetNeedRenewalFalse(long id, LocalDate newDate) throws DaoException;

    /**
     * Makes prescription need renewal.
     *
     * @param id the id
     * @return the boolean {@code true} if updated
     * @throws DaoException the dao exception
     */
    boolean makeNeedRenewal(long id) throws DaoException;

    /**
     * Finds needed renewal doctor prescriptions data map.
     *
     * @param doctorId the doctor id
     * @return the data map of needed renewal doctor prescriptions
     * @throws DaoException the dao exception
     */
    Map<Long, Map<String, Object>> findNeededRenewalOfDoctor(long doctorId) throws DaoException;

    /**
     * Finds prescription available number.
     *
     * @param prescriptionId the prescription id
     * @return the prescription available number
     * @throws DaoException the dao exception
     */
    int findPrescriptionAvailableNumber(long prescriptionId) throws DaoException;

    /**
     * Increases prescription sold quantity.
     *
     * @param prescriptionId   the prescription id
     * @param medicineId       the medicine id
     * @param medicinePackages the medicine packages
     * @return the boolean {@code true} if increases
     * @throws DaoException the dao exception
     */
    boolean increaseSoldQuantity(long prescriptionId, long medicineId, int medicinePackages) throws DaoException;
}
