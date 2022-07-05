package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.model.entity.DosageUnit;
import com.epam.pharmacy.model.entity.FormUnit;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.*;

/**
 * The type Prescription row mapper.
 */
public class PrescriptionRowMapper implements CustomRowMapper<Prescription> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static PrescriptionRowMapper instance;

    private PrescriptionRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PrescriptionRowMapper getInstance() {
        if (instance == null) {
            instance = new PrescriptionRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<Prescription> mapRow(ResultSet resultSet) {
        Optional<Prescription> optionalPrescription;
        try {
            long id = resultSet.getLong(PRESCRIPTION_ID);
            long internationalNameId = resultSet.getLong(PRESCRIPTION_INTERNATIONAL_NAME_ID);
            long doctorId = resultSet.getLong(PRESCRIPTION_DOCTOR_ID);
            long customerId = resultSet.getLong(PRESCRIPTION_CUSTOMER_ID);
            String unitString = resultSet.getString(PRESCRIPTION_UNIT);
            FormUnit unit = FormUnit.valueOf(unitString);
            int quantity = resultSet.getInt(PRESCRIPTION_QUANTITY);
            int soldQuantity = resultSet.getInt(PRESCRIPTION_SOLD_QUANTITY);
            Date date = resultSet.getDate(PRESCRIPTION_EXPIRATION_DATE);
            LocalDate expirationDate = date.toLocalDate();
            int dosage = resultSet.getInt(PRESCRIPTION_DOSAGE);
            String dosageUnitString = resultSet.getString(PRESCRIPTION_DOSAGE_UNIT);
            DosageUnit dosageUnit = DosageUnit.valueOf(dosageUnitString);
            boolean needRenewal = resultSet.getBoolean(PRESCRIPTION_NEED_RENEWAL);
            Prescription.Builder builder = new Prescription.Builder(id);
            Prescription prescription = builder.
                    buildInternationalNameId(internationalNameId).
                    buildDoctorId(doctorId).
                    buildCustomerId(customerId).
                    buildQuantity(quantity).
                    buildSoldQuantity(soldQuantity).
                    buildExpirationDate(expirationDate).
                    buildUnit(unit).
                    buildDosage(dosage).
                    buildDosageUnit(dosageUnit).
                    buildNeedRenewal(needRenewal).
                    build();
            optionalPrescription = Optional.of(prescription);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map prescription row. ", e);
            optionalPrescription = Optional.empty();
        }
        return optionalPrescription;
    }
}
