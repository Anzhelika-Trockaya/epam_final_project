package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

/**
 * The interface Manufacturer service.
 */
public interface ManufacturerService {
    /**
     * Create boolean.
     *
     * @param name    the name
     * @param country the country
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(String name, String country) throws ServiceException;

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
     * @param id      the id
     * @param name    the name
     * @param country the country
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Manufacturer> update(String id, String name, String country) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Manufacturer> findAll() throws ServiceException;
}
