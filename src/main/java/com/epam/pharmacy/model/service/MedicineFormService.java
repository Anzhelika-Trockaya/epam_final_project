package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.MedicineForm;

import java.util.List;
import java.util.Optional;

/**
 * The interface Medicine form service.
 */
public interface MedicineFormService {
    /**
     * Creates a new medicine form.
     *
     * @param name the name
     * @param unit the unit
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(String name, String unit) throws ServiceException;

    /**
     * Deletes the medicine form, if it is not using in medicines.
     *
     * @param id the id
     * @return the boolean {@code true} if deleted
     * @throws ServiceException the service exception
     */
    boolean delete(String id) throws ServiceException;

    /**
     * Updates medicine form.
     *
     * @param id   the id
     * @param name the name
     * @param unit the unit
     * @return the old medicine form optional if updated and empty option if not
     * @throws ServiceException the service exception
     */
    Optional<MedicineForm> update(String id, String name, String unit) throws ServiceException;

    /**
     * Finds all medicine forms list.
     *
     * @return the list of medicine forms
     * @throws ServiceException the service exception
     */
    List<MedicineForm> findAll() throws ServiceException;
}
