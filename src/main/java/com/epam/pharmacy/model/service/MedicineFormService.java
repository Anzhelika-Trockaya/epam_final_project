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
     * Create boolean.
     *
     * @param name the name
     * @param unit the unit
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(String name, String unit) throws ServiceException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(String id) throws ServiceException;

    /**
     * Update optional.
     *
     * @param id   the id
     * @param name the name
     * @param unit the unit
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<MedicineForm> update(String id, String name, String unit) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<MedicineForm> findAll() throws ServiceException;
}
