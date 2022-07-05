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
     * Creates a new international name.
     *
     * @param name the name
     * @return the boolean {@code true} if created
     * @throws ServiceException the service exception
     */
    boolean create(String name) throws ServiceException;

    /**
     * Deletes the international name, if it is not using in medicines.
     *
     * @param id the id
     * @return the boolean {@code true} if deleted
     * @throws ServiceException the service exception
     */
    boolean delete(String id) throws ServiceException;

    /**
     * Updates international name.
     *
     * @param id   the id
     * @param name the international name
     * @return the old international name optional if updated and empty optional if not
     * @throws ServiceException the service exception
     */
    Optional<InternationalMedicineName> update(String id, String name) throws ServiceException;

    /**
     * Finds all international names list.
     *
     * @return the list of international names
     * @throws ServiceException the service exception
     */
    List<InternationalMedicineName> findAll() throws ServiceException;
}
