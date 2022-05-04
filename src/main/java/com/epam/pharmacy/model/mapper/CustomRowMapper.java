package com.epam.pharmacy.model.mapper;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.CustomEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper<T extends CustomEntity> {
    Optional<T> mapRow(ResultSet resultSet) throws DaoException;
}
