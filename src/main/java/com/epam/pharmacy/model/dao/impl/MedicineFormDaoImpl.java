package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.MedicineFormDao;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.mapper.impl.MedicineFormRowMapper;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicineFormDaoImpl extends AbstractDao<MedicineForm> implements MedicineFormDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static MedicineFormDaoImpl instance;
    private static final int ONE_UPDATED = 1;
    private static final String SQL_SELECT_ALL_FORMS = "SELECT form_id, form_name, form_unit FROM forms";
    private static final String SQL_INSERT_FORM = "INSERT INTO forms (form_name, form_unit) values(?,?)";

    private MedicineFormDaoImpl() {
    }

    public static MedicineFormDaoImpl getInstance() {
        if (instance == null) {
            instance = new MedicineFormDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean create(MedicineForm medicineForm) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_FORM)) {
            statement.setString(1, medicineForm.getName());
            statement.setString(2, medicineForm.getUnit().name());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding medicine form exception. " + e.getMessage());
            throw new DaoException("Adding medicine form exception. ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
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
            LOGGER.error("Find all medicine forms exception. " + e.getMessage());
            throw new DaoException("Find all medicine forms exception. ", e);
        }
        return forms;
    }

    @Override
    public Optional<MedicineForm> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public MedicineForm update(MedicineForm medicineForm) throws DaoException {
        return null;
    }
}
