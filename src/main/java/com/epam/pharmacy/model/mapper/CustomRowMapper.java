package com.epam.pharmacy.model.mapper;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.CustomEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper<T extends CustomEntity> {
    /**
     * Map row optional.
     *
     * @param resultSet the result set
     * @return the optional
     */
//fixme doc
    Optional<T> mapRow(ResultSet resultSet);
}
