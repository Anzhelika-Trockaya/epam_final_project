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
     * Create new medicine.
     *
     * @param medicineData the medicine data
     * @return the boolean {@code true} if created
     * @throws ServiceException the service exception
     */
    boolean create(Map<String, String> medicineData) throws ServiceException;

    /**
     * Finds medicine by id.
     *
     * @param medicineIdString the medicine id string
     * @return the medicine optional
     * @throws ServiceException the service exception
     */
    Optional<Medicine> findById(String medicineIdString) throws ServiceException;

    /**
     * Finds medicine content by id.
     *
     * @param id the id
     * @return the medicine data map
     * @throws ServiceException the service exception
     */
    Map<String, Object> findMedicineContentById(long id) throws ServiceException;

    /**
     * Finds all medicines data map.
     *
     * @return the data map of medicines
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAll() throws ServiceException;

    /**
     * Updates medicine.
     *
     * @param medicineData the medicine data
     * @return the boolean {@code true} if updated
     * @throws ServiceException the service exception
     */
    boolean update(Map<String, String> medicineData) throws ServiceException;

    /**
     * Finds medicine total packages.
     *
     * @param id the id
     * @return the int optional. Returns empty optional if medicine not found
     * @throws ServiceException the service exception
     */
    Optional<Integer> findTotalPackages(long id) throws ServiceException;

    /**
     * Finds medicines data map by params.
     *
     * @param paramsMap the params map
     * @return the medicines data map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findByParams(Map<String, String> paramsMap) throws ServiceException;

    /**
     * Finds medicines data map by prescription.
     *
     * @param prescription the prescription
     * @return the medicines data map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findByPrescription(Prescription prescription) throws ServiceException;

    /**
     * Find all medicines with positive total packages data map.
     *
     * @return the medicines data map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAllAvailable() throws ServiceException;

    /**
     * Finds the medicines data map with positive total packages, considering the quantity in the customers cart
     *
     * @param customerId the customer id
     * @return the medicines data map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findAllAvailableForCustomer(long customerId) throws ServiceException;

    /**
     * Finds medicines data map by params with positive total packages, considering the quantity in the customers cart
     *
     * @param customerId the customer id
     * @param paramsMap  the params map
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Long, Map<String, Object>> findByParamsForCustomer(long customerId, HashMap<String, String> paramsMap) throws ServiceException;
}
