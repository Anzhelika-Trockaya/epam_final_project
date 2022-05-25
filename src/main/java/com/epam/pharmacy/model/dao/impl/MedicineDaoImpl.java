package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.MedicineDao;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MedicineDaoImpl extends AbstractDao<Medicine> implements MedicineDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SQL_INSERT_MEDICINE =
            "INSERT INTO medicines (medicine_name, international_name_id, medicine_price, medicine_parts_quantity, " +
                    "medicine_parts_amount_in_package, medicine_amount_in_part, form_id, medicine_dosage, " +
                    "medicine_dosage_unit, medicine_ingredients, medicine_need_prescription, manufacturer_id, " +
                    "medicine_instruction, medicine_image_path) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    @Override
    public boolean create(Medicine medicine) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MEDICINE)) {
            statement.setString(1, medicine.getName());
            statement.setLong(2, medicine.getInternationalNameId());
            statement.setBigDecimal(3, medicine.getPrice());
            statement.setInt(4, medicine.getTotalNumberOfParts());
            statement.setInt(5, medicine.getPartsInPackage());
            statement.setInt(6, medicine.getAmountInPart());
            statement.setLong(7, medicine.getFormId());
            statement.setInt(8, medicine.getDosage());
            statement.setString(9, medicine.getDosageUnit().name());
            statement.setString(10, medicine.getIngredients());
            statement.setBoolean(11, medicine.needPrescription());
            statement.setLong(12, medicine.getManufacturerId());
            statement.setString(13, medicine.getInstruction());
            statement.setString(14, medicine.getImagePath());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding medicine exception. " + e.getMessage());
            throw new DaoException("Adding medicine exception. ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Medicine> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Medicine> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Medicine update(Medicine medicine) throws DaoException {
        return null;
    }
}
