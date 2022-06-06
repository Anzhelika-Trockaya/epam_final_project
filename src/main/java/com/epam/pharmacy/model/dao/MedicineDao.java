package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.List;

public interface MedicineDao {
    List<Medicine> findByName(String name) throws DaoException;

    List<Medicine> findByInternationalNameId(long id) throws DaoException;

    boolean updateTotalParts();
}
