package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;

import java.util.List;
import java.util.Optional;

public interface InternationalMedicineNameDao {

    boolean existInternationalName(String name) throws DaoException;
}
