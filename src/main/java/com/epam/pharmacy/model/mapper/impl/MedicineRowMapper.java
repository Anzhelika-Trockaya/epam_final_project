package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.DosageUnit;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.*;

public class MedicineRowMapper implements CustomRowMapper<Medicine> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static MedicineRowMapper instance;

    private MedicineRowMapper() {
    }

    public static MedicineRowMapper getInstance() {
        if (instance == null) {
            instance = new MedicineRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<Medicine> mapRow(ResultSet resultSet) {
        Optional<Medicine> optionalMedicine;
        try {
            long id = resultSet.getLong(MEDICINE_ID);
            String name = resultSet.getString(MEDICINE_NAME);
            long internationalNameId = resultSet.getLong(MEDICINE_INTERNATIONAL_NAME_ID);
            BigDecimal price = resultSet.getBigDecimal(MEDICINE_PRICE);
            int totalPackages = resultSet.getInt(MEDICINE_TOTAL_PACKAGES);
            int numberInPackage = resultSet.getInt(MEDICINE_NUMBER_IN_PACKAGE);
            long formId = resultSet.getLong(MEDICINE_FORM_ID);
            int dosage = resultSet.getInt(MEDICINE_DOSAGE);
            String dosageUnitString = resultSet.getString(MEDICINE_DOSAGE_UNIT);
            DosageUnit dosageUnit = DosageUnit.valueOf(dosageUnitString);
            boolean needPrescription = resultSet.getBoolean(MEDICINE_NEED_PRESCRIPTION);
            long manufacturerId = resultSet.getLong(MEDICINE_MANUFACTURER_ID);
            String imagePath = resultSet.getString(MEDICINE_IMAGE_PATH);
            Medicine medicine = new Medicine.Builder(id).
                    buildName(name).
                    buildInternationalNameId(internationalNameId).
                    buildPrice(price).
                    buildTotalPackages(totalPackages).
                    buildNumberInPackage(numberInPackage).
                    buildFormId(formId).
                    buildDosage(dosage).
                    buildDosageUnit(dosageUnit).
                    buildNeedPrescription(needPrescription).
                    buildManufacturerId(manufacturerId).
                    buildImagePath(imagePath).
                    build();
            optionalMedicine = Optional.of(medicine);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map medicine row. ", e);
            optionalMedicine = Optional.empty();
        }
        return optionalMedicine;
    }
}
