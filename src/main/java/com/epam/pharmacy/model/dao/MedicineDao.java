package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Medicine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MedicineDao {
    List<Medicine> findByPartOfName(String name) throws DaoException;

    List<Medicine> findByInternationalNameId(long id) throws DaoException;

    List<Medicine> findByManufacturerId(long id) throws DaoException;

    List<Medicine> findByFormId(long id) throws DaoException;

    boolean updateTotalPackages(long medicineId, int value) throws DaoException;

    Optional<Integer> findTotalPackages(long id) throws DaoException;

    List<Medicine> findByParams(HashMap<String, String> paramsMap) throws DaoException;
}
