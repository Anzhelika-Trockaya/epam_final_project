package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Prescription;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Medicine service.
 */
public interface MedicineService {
    /**
     * Create boolean.
     *
     * @param medicineData the medicine data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(Map<String, String> medicineData) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param medicineIdString the medicine id string
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Medicine> findById(String medicineIdString) throws ServiceException;

    /**
     * Find medicine content by id map.
     *
     * @param id the id
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<String, Object> findMedicineContentById(long id) throws ServiceException;

    /**
     * Find all map.
     *
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAll() throws ServiceException;

    /**
     * Update boolean.
     *
     * @param medicineData the medicine data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Map<String, String> medicineData) throws ServiceException;

    /**
     * Find total packages optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Integer> findTotalPackages(long id) throws ServiceException;

    /**
     * Find by params map.
     *
     * @param paramsMap the params map
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findByParams(Map<String, String> paramsMap) throws ServiceException;

    /**
     * Find by prescription map.
     *
     * @param prescription the prescription
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findByPrescription(Prescription prescription) throws ServiceException;

    /**
     * Find all available map.
     *
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAllAvailable() throws ServiceException;

    /**
     * Find all available for customer map.
     *
     * @param customerId the customer id
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAllAvailableForCustomer(long customerId) throws ServiceException;

    /**
     * Find by params for customer map.
     *
     * @param customerId          the customer id
     * @param stringStringHashMap the string string hash map
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findByParamsForCustomer(long customerId, HashMap<String, String> stringStringHashMap) throws ServiceException;
}
