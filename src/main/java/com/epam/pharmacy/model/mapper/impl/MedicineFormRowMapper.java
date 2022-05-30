package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.FormUnit;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.FORM_ID;
import static com.epam.pharmacy.model.dao.ColumnName.FORM_NAME;
import static com.epam.pharmacy.model.dao.ColumnName.FORM_UNIT;

public class MedicineFormRowMapper implements CustomRowMapper<MedicineForm> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static MedicineFormRowMapper instance;

    private MedicineFormRowMapper() {
    }

    public static MedicineFormRowMapper getInstance() {
        if (instance == null) {
            instance = new MedicineFormRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<MedicineForm> mapRow(ResultSet resultSet) throws DaoException {
        Optional<MedicineForm> optionalForm;
        try {
            long id = resultSet.getLong(FORM_ID);
            String name = resultSet.getString(FORM_NAME);
            String unitString = resultSet.getString(FORM_UNIT);
            FormUnit unit=FormUnit.valueOf(unitString);
            MedicineForm form = new MedicineForm(id, name, unit);
            optionalForm = Optional.of(form);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map form row. ", e);
            optionalForm = Optional.empty();//fixme maybe throw daoException
        }
        return optionalForm;
    }
}
