package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

/**
 * The interface International medicine name dao.
 */
public interface InternationalMedicineNameDao {

    /**
     * Exist international name boolean.
     *
     * @param name the name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean existInternationalName(String name) throws DaoException;
}
