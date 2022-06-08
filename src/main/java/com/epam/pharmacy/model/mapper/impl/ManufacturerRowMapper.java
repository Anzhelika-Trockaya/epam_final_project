package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Manufacturer;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.MANUFACTURER_ID;
import static com.epam.pharmacy.model.dao.ColumnName.MANUFACTURER_NAME;
import static com.epam.pharmacy.model.dao.ColumnName.MANUFACTURER_COUNTRY;
import static com.epam.pharmacy.model.dao.ColumnName.MANUFACTURER_ADDRESS;

public class ManufacturerRowMapper implements CustomRowMapper<Manufacturer> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ManufacturerRowMapper instance;

    private ManufacturerRowMapper() {
    }

    public static ManufacturerRowMapper getInstance() {
        if (instance == null) {
            instance = new ManufacturerRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<Manufacturer> mapRow(ResultSet resultSet) throws DaoException {
        Optional<Manufacturer> optionalManufacturer;
        try {
            long id = resultSet.getLong(MANUFACTURER_ID);
            String name = resultSet.getString(MANUFACTURER_NAME);
            String country = resultSet.getString(MANUFACTURER_COUNTRY);
            Manufacturer manufacturer=new Manufacturer(id, name, country);
            optionalManufacturer = Optional.of(manufacturer);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map manufacturer row. ", e);
            optionalManufacturer = Optional.empty();//fixme maybe throw daoException
        }
        return optionalManufacturer;
    }
}
