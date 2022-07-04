package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Manufacturer;

import java.util.Optional;

public interface ManufacturerDao {
    Optional<Manufacturer> findByName(String name) throws DaoException;
}
