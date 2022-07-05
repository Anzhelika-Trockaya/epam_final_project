package com.epam.pharmacy.model.mapper;

import com.epam.pharmacy.model.entity.AbstractEntity;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * The interface Custom row mapper.
 *
 * @param <T> the type parameter
 */
public interface CustomRowMapper<T extends AbstractEntity> {
    /**
     * Map database row to entity.
     *
     * @param resultSet the result set
     * @return the entity optional
     */
    Optional<T> mapRow(ResultSet resultSet);
}
