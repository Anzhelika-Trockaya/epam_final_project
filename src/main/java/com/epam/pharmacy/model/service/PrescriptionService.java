package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;

import java.util.Map;
import java.util.Optional;

/**
 * The interface Prescription service.
 */
public interface PrescriptionService {

    /**
     * Renewals prescription for a month since current day.
     *
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if updated
     * @throws ServiceException the service exception
     */
    boolean renewalForAMonth(String prescriptionId) throws ServiceException;

    /**
     * Finds all doctors prescriptions data map.
     *
     * @param doctorId the doctor id
     * @return the data map of doctors prescriptions
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAllByDoctor(long doctorId) throws ServiceException;

    /**
     * Finds doctors renewal requests data map.
     *
     * @param doctorId the doctor id
     * @return the data map of doctors renewal requests.
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findRenewalRequestsByDoctor(long doctorId) throws ServiceException;

    /**
     * Finds all customers prescriptions data map.
     *
     * @param customerId the customer id
     * @return the data map of customers prescriptions
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAllByCustomer(long customerId) throws ServiceException;

    /**
     * Creates new prescription.
     *
     * @param doctorId the doctor id
     * @param data     the data
     * @return the boolean {@code true} if created
     * @throws ServiceException the service exception
     */
    boolean create(long doctorId, Map<String, String> data) throws ServiceException;

    /**
     * Makes prescription need renewal.
     *
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if updated
     * @throws ServiceException the service exception
     */
    boolean makeNeedRenewal(String prescriptionId) throws ServiceException;

    /**
     * Finds prescription by id.
     *
     * @param prescriptionId the prescription id String
     * @return the prescription optional. Returns empty optional if not found.
     * @throws ServiceException the service exception
     */
    Optional<Prescription> findById(String prescriptionId) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param id the id long
     * @return the prescription optional. Returns empty optional if not found.
     * @throws ServiceException the service exception
     */
    Optional<Prescription> findById(long id) throws ServiceException;

    /**
     * Deletes prescription if is not used.
     *
     * @param prescriptionId the prescription id
     * @return the boolean {@code true} if deleted
     * @throws ServiceException the service exception
     */
    boolean deleteIfIsNotUsed(String prescriptionId) throws ServiceException;
}
