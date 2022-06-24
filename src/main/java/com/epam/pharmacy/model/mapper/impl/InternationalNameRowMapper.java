package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.INTERNATIONAL_MEDICINE_NAME;
import static com.epam.pharmacy.model.dao.ColumnName.INTERNATIONAL_MEDICINE_NAME_ID;

public class InternationalNameRowMapper implements CustomRowMapper<InternationalMedicineName> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static InternationalNameRowMapper instance;

    private InternationalNameRowMapper() {
    }

    public static InternationalNameRowMapper getInstance() {
        if (instance == null) {
            instance = new InternationalNameRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<InternationalMedicineName> mapRow(ResultSet resultSet) throws DaoException {
        Optional<InternationalMedicineName> optionalInternationalName;
        try {
            long id = resultSet.getLong(INTERNATIONAL_MEDICINE_NAME_ID);
            String name = resultSet.getString(INTERNATIONAL_MEDICINE_NAME);
            InternationalMedicineName internationalName = new InternationalMedicineName(id, name);
            optionalInternationalName = Optional.of(internationalName);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map International medicine name row. ", e);
            optionalInternationalName = Optional.empty();
        }
        return optionalInternationalName;
    }
}
