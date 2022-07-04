package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

public interface InternationalMedicineNameDao {

    boolean existInternationalName(String name) throws DaoException;
}
