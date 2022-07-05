package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;

import java.util.List;
import java.util.Optional;

/**
 * The interface International name service.
 */
public interface InternationalNameService {
    /**
     * Create boolean.
     *
     * @param name the name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(String name) throws ServiceException;

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
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<InternationalMedicineName> update(String id, String name) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<InternationalMedicineName> findAll() throws ServiceException;
}
