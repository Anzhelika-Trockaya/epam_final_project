package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

public interface MedicineFormDao {
    boolean existFormName(String name) throws DaoException;
}
