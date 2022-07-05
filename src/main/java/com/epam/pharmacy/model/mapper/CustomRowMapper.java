package com.epam.pharmacy.model.mapper;

import com.epam.pharmacy.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

public interface CustomRowMapper<T extends AbstractEntity> {
    /**
     * Map row optional.
     *
     * @param resultSet the result set
     * @return the optional
     */
//fixme doc
    Optional<T> mapRow(ResultSet resultSet);
}
