package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Manufacturer;

import java.util.Optional;

/**
 * The interface Manufacturer dao.
 */
public interface ManufacturerDao {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Manufacturer> findByName(String name) throws DaoException;
}
