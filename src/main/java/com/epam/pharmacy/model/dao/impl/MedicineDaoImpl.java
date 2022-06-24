package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.MedicineDao;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.mapper.impl.MedicineRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.pharmacy.model.dao.ColumnName.*;

public class MedicineDaoImpl extends AbstractDao<Medicine> implements MedicineDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String PERCENT = "%";
    private static final String SQL_INSERT_MEDICINE =
            "INSERT INTO medicines (medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_MEDICINES =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines";
    private static final String SQL_SELECT_MEDICINE_BY_ID =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines WHERE medicine_id = ?";
    private static final String SQL_SELECT_MEDICINE_BY_PART_OF_NAME =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines WHERE medicine_name LIKE ?";
    private static final String SQL_SELECT_MEDICINE_BY_INTERNATIONAL_NAME_ID =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines WHERE medicine_international_name_id = ?";
    private static final String SQL_SELECT_MEDICINE_BY_MANUFACTURER_ID =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines WHERE medicine_manufacturer_id = ?";
    private static final String SQL_SELECT_MEDICINE_BY_FORM_ID =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines WHERE medicine_form_id = ?";
    private static final String SQL_SELECT_MEDICINE_TOTAL_PACKAGES_BY_ID =
            "SELECT medicine_total_packages FROM medicines WHERE medicine_id = ?";
    private static final String SQL_SELECT_MEDICINE_BY_NAME_INTERNATIONAL_NAME_ID_FORM_ID_DOSAGE_DOSAGE_UNIT =
            "SELECT medicine_id, medicine_name, medicine_international_name_id, medicine_price, " +
                    "medicine_total_packages, medicine_number_in_package, " +
                    "medicine_form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, " +
                    "medicine_need_prescription, medicine_manufacturer_id, medicine_instruction, medicine_image_path " +
                    "FROM medicines WHERE medicine_name LIKE UPPER(?) AND medicine_international_name_id LIKE ? AND " +
                    "medicine_form_id LIKE ? AND medicine_dosage LIKE ? AND medicine_dosage_unit LIKE ?";
    private static final String SQL_UPDATE_MEDICINE_BY_ID =
            "UPDATE medicines SET medicine_name = ?, medicine_international_name_id = ?, medicine_price = ?, " +
                    "medicine_number_in_package = ?, medicine_form_id = ?, medicine_dosage = ?, " +
                    "medicine_dosage_unit = ?, medicine_ingredients = ?, " +
                    "medicine_need_prescription = ?, medicine_manufacturer_id = ?, medicine_instruction = ?, " +
                    "medicine_image_path = ? WHERE medicine_id = ?";
    private static final String SQL_UPDATE_MEDICINE_TOTAL_PACKAGES_BY_ID =
            "UPDATE medicines SET medicine_total_packages = medicine_total_packages + ? WHERE medicine_id = ?";
    private static final String SQL_DELETE_MEDICINE_BY_ID = "DELETE FROM medicines WHERE medicine_id = ?";

    @Override
    public boolean create(Medicine medicine) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MEDICINE)) {
            statement.setString(1, medicine.getName());
            statement.setLong(2, medicine.getInternationalNameId());
            statement.setBigDecimal(3, medicine.getPrice());
            statement.setInt(4, medicine.getTotalPackages());
            statement.setInt(5, medicine.getNumberInPackage());
            statement.setLong(6, medicine.getFormId());
            statement.setInt(7, medicine.getDosage());
            statement.setString(8, medicine.getDosageUnit().name());
            statement.setString(9, medicine.getIngredients());
            statement.setBoolean(10, medicine.needPrescription());
            statement.setLong(11, medicine.getManufacturerId());
            statement.setString(12, medicine.getInstruction());
            statement.setString(13, medicine.getImagePath());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding medicine exception." + medicine, e);
            throw new DaoException("Adding medicine exception." + medicine, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MEDICINE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Delete medicine by id exception. id=" + id, e);
            throw new DaoException("Delete medicine by id exception. id=" + id, e);
        }
    }

    @Override
    public List<Medicine> findAll() throws DaoException {
        List<Medicine> medicines = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_MEDICINES);
             ResultSet resultSet = statement.executeQuery()) {
            MedicineRowMapper mapper = MedicineRowMapper.getInstance();
            Optional<Medicine> currentMedicineOptional;
            while (resultSet.next()) {
                currentMedicineOptional = mapper.mapRow(resultSet);
                currentMedicineOptional.ifPresent(medicines::add);
            }
        } catch (SQLException e) {
            LOGGER.error("Find all medicines exception.", e);
            throw new DaoException("Find all medicines exception.", e);
        }
        return medicines;
    }

    @Override
    public Optional<Medicine> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                MedicineRowMapper mapper = MedicineRowMapper.getInstance();
                Optional<Medicine> medicineOptional;
                if (resultSet.next()) {
                    medicineOptional = mapper.mapRow(resultSet);
                } else {
                    medicineOptional = Optional.empty();
                }
                return medicineOptional;
            }
        } catch (SQLException e) {
            LOGGER.error("Find medicine by id exception. id=" + id, e);
            throw new DaoException("Find medicine by id exception. id=" + id, e);
        }
    }

    @Override
    public List<Medicine> findByPartOfName(String name) throws DaoException {//fixme delete
        List<Medicine> medicines = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_BY_PART_OF_NAME)) {
            statement.setString(1, PERCENT + name + PERCENT);
            try (ResultSet resultSet = statement.executeQuery()) {
                MedicineRowMapper mapper = MedicineRowMapper.getInstance();
                Optional<Medicine> currentMedicineOptional;
                while (resultSet.next()) {
                    currentMedicineOptional = mapper.mapRow(resultSet);
                    currentMedicineOptional.ifPresent(medicines::add);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Find medicine by part of name exception. name=" + name, e);
            throw new DaoException("Find medicine by part of name exception. name=" + name, e);
        }
        return medicines;
    }

    @Override
    public List<Medicine> findByInternationalNameId(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_BY_INTERNATIONAL_NAME_ID)) {
            statement.setLong(1, id);
            return findMedicines(statement);
        } catch (SQLException e) {
            LOGGER.error("Find medicine by international name exception. international name id =" + id, e);
            throw new DaoException("Find medicine by international name exception. international name id =" + id, e);
        }
    }

    @Override
    public List<Medicine> findByManufacturerId(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_BY_MANUFACTURER_ID)) {
            statement.setLong(1, id);
            return findMedicines(statement);
        } catch (SQLException e) {
            LOGGER.error("Find medicine by manufacturer exception. international name id =" + id, e);
            throw new DaoException("Find medicine by manufacturer exception. international name id =" + id, e);
        }
    }

    @Override
    public List<Medicine> findByFormId(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_BY_FORM_ID)) {
            statement.setLong(1, id);
            return findMedicines(statement);
        } catch (SQLException e) {
            LOGGER.error("Find medicine by form exception. international name id =" + id, e);
            throw new DaoException("Find medicine by form exception. international name id =" + id, e);
        }
    }

    @Override
    public Optional<Integer> findTotalPackages(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_TOTAL_PACKAGES_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int total = resultSet.getInt(MEDICINE_TOTAL_PACKAGES);
                    return Optional.of(total);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Find total of medicine by id exception. id=" + id, e);
            throw new DaoException("Find total of medicine by id exception. id=" + id, e);
        }
    }

    @Override
    public Optional<Medicine> update(Medicine medicine) throws DaoException {
        Optional<Medicine> oldMedicineOptional = findById(medicine.getId());
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MEDICINE_BY_ID)) {
            statement.setString(1, medicine.getName());
            statement.setLong(2, medicine.getInternationalNameId());
            statement.setBigDecimal(3, medicine.getPrice());
            statement.setInt(4, medicine.getNumberInPackage());
            statement.setLong(5, medicine.getFormId());
            statement.setInt(6, medicine.getDosage());
            statement.setString(7, medicine.getDosageUnit().name());
            statement.setString(8, medicine.getIngredients());
            statement.setBoolean(9, medicine.needPrescription());
            statement.setLong(10, medicine.getManufacturerId());
            statement.setString(11, medicine.getInstruction());
            statement.setString(12, medicine.getImagePath());
            statement.setLong(13, medicine.getId());
            return statement.executeUpdate() == ONE_UPDATED ? oldMedicineOptional : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Edit medicine exception." + medicine, e);
            throw new DaoException("Edit medicine exception." + medicine, e);
        }
    }

    @Override
    public boolean updateTotalPackages(long medicineId, int value) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MEDICINE_TOTAL_PACKAGES_BY_ID)) {
            statement.setInt(1, value);
            statement.setLong(2, medicineId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Update medicine total packages exception. MedicineId=" + medicineId +
                    " value=" + value, e);
            throw new DaoException("Update medicine total packages exception. MedicineId=" + medicineId +
                    " value=" + value, e);
        }
    }

    @Override
    public List<Medicine> findByParams(HashMap<String, String> paramsMap) throws DaoException {
        try (PreparedStatement statement = connection.
                prepareStatement(SQL_SELECT_MEDICINE_BY_NAME_INTERNATIONAL_NAME_ID_FORM_ID_DOSAGE_DOSAGE_UNIT)) {
            String medicineName = paramsMap.get(MEDICINE_NAME);
            if (!medicineName.equals(PERCENT)) {
                medicineName = PERCENT + medicineName.trim() + PERCENT;
            }
            statement.setString(1, medicineName);
            statement.setString(2, paramsMap.get(MEDICINE_INTERNATIONAL_NAME_ID));
            statement.setString(3, paramsMap.get(MEDICINE_FORM_ID));
            statement.setString(4, paramsMap.get(MEDICINE_DOSAGE));
            statement.setString(5, paramsMap.get(MEDICINE_DOSAGE_UNIT));
            return findMedicines(statement);
        } catch (SQLException e) {
            LOGGER.error("Exception when find medicines by params." + paramsMap, e);
            throw new DaoException("Exception when find medicines by params." + paramsMap, e);
        }
    }

    private List<Medicine> findMedicines(PreparedStatement statement) throws SQLException, DaoException {
        List<Medicine> medicines = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            MedicineRowMapper mapper = MedicineRowMapper.getInstance();
            Optional<Medicine> currentMedicineOptional;
            while (resultSet.next()) {
                currentMedicineOptional = mapper.mapRow(resultSet);
                currentMedicineOptional.ifPresent(medicines::add);
            }
        }
        return medicines;
    }
}
