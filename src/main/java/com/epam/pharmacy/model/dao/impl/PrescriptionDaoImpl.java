package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.ColumnName;
import com.epam.pharmacy.model.dao.PrescriptionDao;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.mapper.impl.PrescriptionRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.QUANTITY;
import static com.epam.pharmacy.model.dao.ColumnName.*;
import static com.epam.pharmacy.model.dao.ColumnName.PRESCRIPTION_UNIT;
import static com.epam.pharmacy.model.dao.ColumnName.USER_BIRTHDAY_DATE;
import static com.epam.pharmacy.model.dao.ColumnName.USER_LASTNAME;
import static com.epam.pharmacy.model.dao.ColumnName.USER_NAME;
import static com.epam.pharmacy.model.dao.ColumnName.USER_PATRONYMIC;

public class PrescriptionDaoImpl extends AbstractDao<Prescription> implements PrescriptionDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final int AVAILABLE_QUANTITY_IF_NOT_EXISTS = -1;
    private static final char SPACE = ' ';
    private static final String SQL_INSERT_PRESCRIPTION =
            "INSERT INTO prescriptions (prescription_international_name_id, prescription_doctor_id, " +
                    "prescription_customer_id, prescription_quantity, prescription_expiration_date, " +
                    "prescription_unit, prescription_dosage, prescription_dosage_unit, " +
                    "prescription_need_renewal) values(?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_PRESCRIPTIONS =
            "SELECT prescription_id, prescription_international_name_id, prescription_doctor_id, " +
                    "prescription_customer_id, prescription_quantity, prescription_sold_quantity, " +
                    "prescription_expiration_date, prescription_unit, prescription_dosage, " +
                    "prescription_dosage_unit, prescription_need_renewal FROM prescriptions";
    private static final String SQL_SELECT_PRESCRIPTION_BY_ID =
            "SELECT prescription_id, prescription_international_name_id, prescription_doctor_id, " +
                    "prescription_customer_id, prescription_quantity, prescription_sold_quantity, " +
                    "prescription_expiration_date, prescription_unit, prescription_dosage, " +
                    "prescription_dosage_unit, prescription_need_renewal FROM prescriptions WHERE prescription_id = ?";
    private static final String SQL_SELECT_AVAILABLE_QUANTITY_BY_PRESCRIPTION_ID =
            "SELECT prescription_quantity - prescription_sold_quantity AS available_quantity FROM prescriptions " +
                    "WHERE prescription_id = ?";
    private static final String SQL_SELECT_BY_DOCTOR_ID =
            "SELECT prescription_id, international_medicine_name, prescription_unit, " +
                    "user_lastname, user_name, user_patronymic, user_birthday_date, prescription_quantity, " +
                    "prescription_sold_quantity, prescription_expiration_date, prescription_dosage, " +
                    "prescription_dosage_unit, prescription_need_renewal FROM prescriptions p " +
                    "JOIN international_medicines_names i ON p.prescription_international_name_id =" +
                    "i.international_medicine_name_id " +
                    "JOIN users u ON p.prescription_customer_id = u.user_id " +
                    "WHERE p.prescription_doctor_id = ?";
    private static final String SQL_SELECT_NEEDED_RENEWAL_BY_DOCTOR_ID =
            "SELECT prescription_id, international_medicine_name, prescription_unit, " +
                    "user_lastname, user_name, user_patronymic, user_birthday_date, prescription_quantity, " +
                    "prescription_sold_quantity, prescription_expiration_date, prescription_dosage, " +
                    "prescription_dosage_unit, prescription_need_renewal FROM prescriptions p " +
                    "JOIN international_medicines_names i ON p.prescription_international_name_id =" +
                    "i.international_medicine_name_id " +
                    "JOIN users u ON p.prescription_customer_id = u.user_id " +
                    "WHERE p.prescription_doctor_id = ? AND prescription_need_renewal = true";
    private static final String SQL_SELECT_BY_CUSTOMER_ID =
            "SELECT prescription_id, international_medicine_name, prescription_unit, user_lastname, user_name, " +
                    "user_patronymic, prescription_quantity, prescription_sold_quantity, " +
                    "prescription_expiration_date, prescription_dosage, prescription_dosage_unit, " +
                    "prescription_need_renewal FROM prescriptions p " +
                    "JOIN international_medicines_names i ON p.prescription_international_name_id =" +
                    "i.international_medicine_name_id " +
                    "JOIN users u ON p.prescription_doctor_id = u.user_id " +
                    "WHERE p.prescription_customer_id = ?";
    private static final String SQL_UPDATE_PRESCRIPTION_BY_ID =
            "UPDATE prescriptions SET prescription_international_name_id = ?, prescription_doctor_id = ?, " +
                    "prescription_customer_id = ?, prescription_quantity = ?, prescription_expiration_date = ?, " +
                    "prescription_unit = ?, prescription_dosage = ?, prescription_dosage_unit = ?, " +
                    "prescription_need_renewal = ?, prescription_sold_quantity = ? WHERE prescription_id = ?";
    private static final String SQL_INCREASE_PRESCRIPTION_SOLD_QUANTITY_BY_ID =
            "UPDATE prescriptions SET prescription_sold_quantity = prescription_sold_quantity + " +
                    "(SELECT medicine_number_in_package * ? FROM medicines WHERE medicine_id = ?) " +
                    "WHERE prescription_id = ?";
    private static final String SQL_UPDATE_PRESCRIPTION_EXPIRATION_DATE_AND_NEED_RENEWAL_MAKE_FALSE_BY_ID =
            "UPDATE prescriptions SET prescription_expiration_date = ?, prescription_need_renewal = false WHERE prescription_id = ?";
    private static final String SQL_UPDATE_MAKE_NEED_RENEWAL_TRUE_BY_ID =
            "UPDATE prescriptions SET prescription_need_renewal = true WHERE prescription_id = ?";
    private static final String SQL_DELETE_PRESCRIPTION_BY_ID = "DELETE FROM prescriptions WHERE prescription_id = ?";

    @Override
    public boolean create(Prescription prescription) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRESCRIPTION)) {
            statement.setLong(1, prescription.getInternationalNameId());
            statement.setLong(2, prescription.getDoctorId());
            statement.setLong(3, prescription.getCustomerId());
            statement.setInt(4, prescription.getQuantity());
            statement.setDate(5, Date.valueOf(prescription.getExpirationDate()));
            statement.setString(6, prescription.getUnit().name());
            statement.setInt(7, prescription.getDosage());
            statement.setString(8, prescription.getDosageUnit().name());
            statement.setBoolean(9, prescription.isNeedRenewal());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding prescription exception." + prescription, e);
            throw new DaoException("Adding prescription exception." + prescription, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRESCRIPTION_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Delete prescription by id exception. id=" + id, e);
            throw new DaoException("Delete prescription by id exception. id=" + id, e);
        }
    }

    @Override
    public List<Prescription> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_PRESCRIPTIONS)) {
            return findPrescriptionsDataByStatement(statement);
        } catch (SQLException e) {
            LOGGER.error("Find all prescriptions exception.", e);
            throw new DaoException("Find all prescriptions exception.", e);
        }
    }

    @Override
    public Optional<Prescription> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRESCRIPTION_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                PrescriptionRowMapper mapper = PrescriptionRowMapper.getInstance();
                Optional<Prescription> prescriptionOptional;
                if (resultSet.next()) {
                    prescriptionOptional = mapper.mapRow(resultSet);
                } else {
                    prescriptionOptional = Optional.empty();
                }
                return prescriptionOptional;
            }
        } catch (SQLException e) {
            LOGGER.error("Find prescription by id exception. id=" + id, e);
            throw new DaoException("Find prescription by id exception. id=" + id, e);
        }
    }

    @Override
    public Optional<Prescription> update(Prescription prescription) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRESCRIPTION_BY_ID)) {
            statement.setLong(1, prescription.getInternationalNameId());
            statement.setLong(2, prescription.getDoctorId());
            statement.setLong(3, prescription.getCustomerId());
            statement.setInt(4, prescription.getQuantity());
            statement.setDate(5, Date.valueOf(prescription.getExpirationDate()));
            statement.setString(6, prescription.getUnit().name());
            statement.setInt(7, prescription.getDosage());
            statement.setString(8, prescription.getDosageUnit().name());
            statement.setBoolean(9, prescription.isNeedRenewal());
            statement.setInt(10, prescription.getSoldQuantity());
            return statement.executeUpdate() == ONE_UPDATED ? Optional.of(prescription) : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Updating prescription exception." + prescription, e);
            throw new DaoException("Updating prescription exception." + prescription, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findAllOfDoctor(long id) throws DaoException {
        Map<Long, Map<String, Object>> resultMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_DOCTOR_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long prescriptionId = resultSet.getLong(ColumnName.PRESCRIPTION_ID);
                    Map<String, Object> prescriptionDataMap = extractPrescriptionsByCustomerData(resultSet);
                    String birthdayDate = resultSet.getDate(USER_BIRTHDAY_DATE).toString();
                    prescriptionDataMap.put(BIRTHDAY_DATE, birthdayDate);
                    resultMap.put(prescriptionId, prescriptionDataMap);
                }
                return resultMap;
            }
        } catch (SQLException e) {
            LOGGER.error("Find doctor's prescriptions exception. doctorId=" + id, e);
            throw new DaoException("Find doctor's prescriptions exception. doctorId=" + id, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findNeededRenewalOfDoctor(long id) throws DaoException {
        Map<Long, Map<String, Object>> resultMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_NEEDED_RENEWAL_BY_DOCTOR_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long prescriptionId = resultSet.getLong(ColumnName.PRESCRIPTION_ID);
                    Map<String, Object> prescriptionDataMap = extractPrescriptionsByCustomerData(resultSet);
                    String birthdayDate = resultSet.getDate(USER_BIRTHDAY_DATE).toString();
                    prescriptionDataMap.put(BIRTHDAY_DATE, birthdayDate);
                    resultMap.put(prescriptionId, prescriptionDataMap);
                }
                return resultMap;
            }
        } catch (SQLException e) {
            LOGGER.error("Find doctor's needed renewal prescriptions exception. doctorId=" + id, e);
            throw new DaoException("Find doctor's prescriptions needed renewal exception. doctorId=" + id, e);
        }
    }

    @Override
    public Map<Long, Map<String, Object>> findAllOfCustomer(long id) throws DaoException {
        Map<Long, Map<String, Object>> resultMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_CUSTOMER_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long prescriptionId = resultSet.getLong(ColumnName.PRESCRIPTION_ID);
                    Map<String, Object> prescriptionDataMap = extractPrescriptionsByCustomerData(resultSet);
                    resultMap.put(prescriptionId, prescriptionDataMap);
                }
                return resultMap;
            }
        } catch (SQLException e) {
            LOGGER.error("Find customer's prescriptions exception. customerId=" + id, e);
            throw new DaoException("Find customer's prescriptions exception. customerId=" + id, e);
        }
    }

    @Override
    public boolean updateExpirationDateAndSetNeedRenewalFalse(long id, LocalDate newDate) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(
                SQL_UPDATE_PRESCRIPTION_EXPIRATION_DATE_AND_NEED_RENEWAL_MAKE_FALSE_BY_ID)) {
            statement.setDate(1, Date.valueOf(newDate));
            statement.setLong(2, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Updating prescription expiration date exception. id=" + id + " newDate=" +
                    newDate, e);
            throw new DaoException("Updating prescription expiration date exception. id=" + id + " newDate=" +
                    newDate, e);
        }
    }

    @Override
    public boolean makeNeedRenewal(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MAKE_NEED_RENEWAL_TRUE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Updating prescription exception when make need_renewal=true. id=" + id, e);
            throw new DaoException("Updating prescription exception when make need_renewal=true. id=" + id, e);
        }
    }

    @Override
    public int findPrescriptionAvailableNumber(long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AVAILABLE_QUANTITY_BY_PRESCRIPTION_ID)) {
            statement.setLong(1, prescriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(ColumnName.AVAILABLE_QUANTITY);
                } else {
                    LOGGER.warn("Prescription with id=" + prescriptionId + " not found");
                    return AVAILABLE_QUANTITY_IF_NOT_EXISTS;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Find prescription available quantity exception. prescriptionId=" + prescriptionId, e);
            throw new DaoException("Find prescription available quantity exception. prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public boolean increaseSoldQuantity(long prescriptionId, long medicineId, int medicinePackages) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INCREASE_PRESCRIPTION_SOLD_QUANTITY_BY_ID)) {
            statement.setInt(1, medicinePackages);
            statement.setLong(2, medicineId);
            statement.setLong(3, prescriptionId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Increase prescription sold quantity exception. prescriptionId=" + prescriptionId +
                    ", medicineId=" + medicineId + ", medicinePackages=" + medicinePackages, e);
            throw new DaoException("Increase prescription sold quantity exception. prescriptionId=" + prescriptionId +
                    ", medicineId=" + medicineId + ", medicinePackages=" + medicinePackages, e);
        }
    }

    private List<Prescription> findPrescriptionsDataByStatement(PreparedStatement statement) throws SQLException, DaoException {
        List<Prescription> prescriptions = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            PrescriptionRowMapper mapper = PrescriptionRowMapper.getInstance();
            Optional<Prescription> currentPrescriptionOptional;
            while (resultSet.next()) {
                currentPrescriptionOptional = mapper.mapRow(resultSet);
                currentPrescriptionOptional.ifPresent(prescriptions::add);
            }
        }
        return prescriptions;
    }

    private Map<String, Object> extractPrescriptionsByCustomerData(ResultSet resultSet) throws SQLException {
        Map<String, Object> prescriptionDataMap = new HashMap<>();
        String lastname = resultSet.getString(USER_LASTNAME);
        String name = resultSet.getString(USER_NAME);
        String patronymic = resultSet.getString(USER_PATRONYMIC);
        String fullName = new StringBuilder(lastname).append(SPACE).
                append(name).append(SPACE).append(patronymic).toString();
        prescriptionDataMap.put(USER_FULL_NAME, fullName);
        String internationalName = resultSet.getString(INTERNATIONAL_MEDICINE_NAME);
        prescriptionDataMap.put(INTERNATIONAL_NAME, internationalName);
        String unit = resultSet.getString(PRESCRIPTION_UNIT);
        prescriptionDataMap.put(PRESCRIPTION_UNIT, unit);
        int dosage = resultSet.getInt(ColumnName.PRESCRIPTION_DOSAGE);
        prescriptionDataMap.put(DOSAGE, dosage);
        String dosageUnit = resultSet.getString(ColumnName.PRESCRIPTION_DOSAGE_UNIT);
        prescriptionDataMap.put(DOSAGE_UNIT, dosageUnit);
        int quantity = resultSet.getInt(ColumnName.PRESCRIPTION_QUANTITY);
        prescriptionDataMap.put(QUANTITY, quantity);
        int soldQuantity = resultSet.getInt(ColumnName.PRESCRIPTION_SOLD_QUANTITY);
        prescriptionDataMap.put(SOLD_QUANTITY, soldQuantity);
        LocalDate expirationDate = resultSet.getDate(PRESCRIPTION_EXPIRATION_DATE).toLocalDate();
        prescriptionDataMap.put(EXPIRATION_DATE, expirationDate);
        boolean needRenewal = resultSet.getBoolean(ColumnName.PRESCRIPTION_NEED_RENEWAL);
        prescriptionDataMap.put(ParameterName.NEED_RENEWAL, needRenewal);
        return prescriptionDataMap;
    }
}
