package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Prescription;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Medicine dao.
 */
public interface MedicineDao {

    /**
     * Finds medicines with positive total packages.
     *
     * @return the list of medicines with positive total packages
     * @throws DaoException the dao exception
     */
    List<Medicine> findWithPositiveTotalPackages() throws DaoException;

    /**
     * Finds available for customer medicines.
     *
     * @param customerId the customer id
     * @return the list of available for customer medicines
     * @throws DaoException the dao exception
     */
    List<Medicine> findAvailableForCustomer(long customerId) throws DaoException;

    /**
     * Finds medicines by params.
     *
     * @param paramsMap the params map
     * @return the list of medicines with same params values
     * @throws DaoException the dao exception
     */
    List<Medicine> findByParams(Map<String, String> paramsMap) throws DaoException;

    /**
     * Finds by params available for customer medicines.
     *
     * @param customerId the customer id
     * @param paramsMap  the params map
     * @return the list of available for customer medicines
     * @throws DaoException the dao exception
     */
    List<Medicine> findByParamsAvailableForCustomer(long customerId, HashMap<String, String> paramsMap) throws DaoException;

    /**
     * Finds medicines suitable to prescription.
     *
     * @param prescription the prescription
     * @return the list of medicines to prescription
     * @throws DaoException the dao exception
     */
    List<Medicine> findByPrescription(Prescription prescription) throws DaoException;

    /**
     * Finds medicines with international name id.
     *
     * @param id the id
     * @return the list of medicines with international name id
     * @throws DaoException the dao exception
     */
    List<Medicine> findByInternationalNameId(long id) throws DaoException;

    /**
     * Finds medicines with manufacturer id.
     *
     * @param id the id
     * @return the list of medicines with manufacturer id
     * @throws DaoException the dao exception
     */
    List<Medicine> findByManufacturerId(long id) throws DaoException;

    /**
     * Finds medicines with form id.
     *
     * @param id the id
     * @return the list of medicines with form id
     * @throws DaoException the dao exception
     */
    List<Medicine> findByFormId(long id) throws DaoException;

    /**
     * Increases total packages in medicine.
     *
     * @param medicineId the medicine id
     * @param value      the value to increase
     * @return the boolean {@code true} if increased
     * @throws DaoException the dao exception
     */
    boolean increaseTotalPackages(long medicineId, int value) throws DaoException;

    /**
     * Finds medicine total packages optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Integer> findTotalPackages(long id) throws DaoException;

    /**
     * Finds medicine price optional.
     *
     * @param medicineId the medicine id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<BigDecimal> findMedicinePrice(long medicineId) throws DaoException;
}
