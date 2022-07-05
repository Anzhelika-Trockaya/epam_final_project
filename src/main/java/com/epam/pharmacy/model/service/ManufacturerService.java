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
     * Creates a new manufacturer.
     *
     * @param name    the name
     * @param country the country
     * @return the boolean {@code true} if created
     * @throws ServiceException the service exception
     */
    boolean create(String name, String country) throws ServiceException;

    /**
     * Delete the manufacturer.
     *
     * @param id the id
     * @return the boolean {@code true} if deleted
     * @throws ServiceException the service exception
     */
    boolean delete(String id) throws ServiceException;

    /**
     * Updates manufacturer.
     *
     * @param id      the id
     * @param name    the name
     * @param country the country
     * @return the old manufacturer optional if updated and empty optional if not
     * @throws ServiceException the service exception
     */
    Optional<Manufacturer> update(String id, String name, String country) throws ServiceException;

    /**
     * Finds all manufacturers list.
     *
     * @return the list of manufacturers
     * @throws ServiceException the service exception
     */
    List<Manufacturer> findAll() throws ServiceException;
}
