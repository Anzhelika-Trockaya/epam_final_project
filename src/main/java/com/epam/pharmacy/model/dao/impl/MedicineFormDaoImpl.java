package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.MedicineFormDao;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.mapper.impl.MedicineFormRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Medicine form dao.
 */
public class MedicineFormDaoImpl extends AbstractDao<MedicineForm> implements MedicineFormDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SQL_SELECT_ALL_FORMS = "SELECT form_id, form_name, form_unit FROM forms";
    private static final String SQL_INSERT_FORM = "INSERT INTO forms (form_name, form_unit) values(?,?)";
    private static final String SQL_SELECT_FORM_BY_ID = "SELECT form_id, form_name, form_unit FROM forms WHERE form_id = ?";
    private static final String SQL_SELECT_FORM_BY_NAME = "SELECT form_id, form_name, form_unit FROM forms WHERE form_name = ?";
    private static final String SQL_UPDATE_FORM_BY_NAME =
            "UPDATE forms SET form_name = ?, form_unit = ? WHERE form_id = ?";
    private static final String SQL_DELETE_FORM_BY_ID = "DELETE FROM forms WHERE form_id = ?";


    @Override
    public boolean create(MedicineForm medicineForm) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FORM)) {
            statement.setString(1, medicineForm.getName());
            statement.setString(2, medicineForm.getUnit().name());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding medicine form exception. ", e);
            throw new DaoException("Adding medicine form exception. ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FORM_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Delete form by id exception. id=" + id, e);
            throw new DaoException("Delete form by id exception. id=" + id, e);
        }
    }

    @Override
    public List<MedicineForm> findAll() throws DaoException {
        List<MedicineForm> forms = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FORMS);
             ResultSet resultSet = statement.executeQuery()) {
            MedicineFormRowMapper mapper = MedicineFormRowMapper.getInstance();
            Optional<MedicineForm> currentFormOptional;
            while (resultSet.next()) {
                currentFormOptional = mapper.mapRow(resultSet);
                currentFormOptional.ifPresent(forms::add);
            }
        } catch (SQLException e) {
            LOGGER.error("Find all medicine forms exception.", e);
            throw new DaoException("Find all medicine forms exception.", e);
        }
        return forms;
    }

    @Override
    public Optional<MedicineForm> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FORM_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                MedicineFormRowMapper mapper = MedicineFormRowMapper.getInstance();
                Optional<MedicineForm> formOptional;
                if (resultSet.next()) {
                    formOptional = mapper.mapRow(resultSet);
                } else {
                    formOptional = Optional.empty();
                }
                return formOptional;
            }
        } catch (SQLException e) {
            LOGGER.error("Find form by id exception.", e);
            throw new DaoException("Find form by id exception.", e);
        }
    }

    @Override
    public Optional<MedicineForm> update(MedicineForm medicineForm) throws DaoException {
        Optional<MedicineForm> oldMedicineForm = findById(medicineForm.getId());
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_FORM_BY_NAME)) {
            statement.setString(1, medicineForm.getName());
            statement.setString(2, medicineForm.getUnit().name());
            statement.setLong(3, medicineForm.getId());
            return statement.executeUpdate() == ONE_UPDATED ? oldMedicineForm : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Update form by id exception.", e);
            throw new DaoException("Update form by id exception.", e);
        }
    }

    @Override
    public boolean existFormName(String name) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FORM_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                MedicineFormRowMapper mapper = MedicineFormRowMapper.getInstance();
                Optional<MedicineForm> currentFormOptional = Optional.empty();
                if (resultSet.next()) {
                    currentFormOptional = mapper.mapRow(resultSet);
                }
                return currentFormOptional.isPresent();
            }
        } catch (SQLException e) {
            LOGGER.error("Checking exists form name exception. name=" + name, e);
            throw new DaoException("Checking exists form name exception. name=" + name, e);
        }
    }
}
