package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

/**
 * The interface Medicine form dao.
 */
public interface MedicineFormDao {
    /**
     * Check if exists form name.
     *
     * @param name the name
     * @return the boolean {@code true} if exists
     * @throws DaoException the dao exception
     */
    boolean existFormName(String name) throws DaoException;
}
