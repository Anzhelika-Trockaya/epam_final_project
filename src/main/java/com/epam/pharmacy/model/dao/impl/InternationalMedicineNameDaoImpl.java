package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.InternationalMedicineNameDao;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.mapper.impl.InternationalNameRowMapper;
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

public class InternationalMedicineNameDaoImpl extends AbstractDao<InternationalMedicineName>
        implements InternationalMedicineNameDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SQL_SELECT_ALL_INTERNATIONAL_NAMES=
            "SELECT international_medicine_name_id, international_medicine_name FROM international_medicines_names";
    private static final String SQL_INSERT_INTERNATIONAL_NAME =
            "INSERT INTO international_medicines_names (international_medicine_name) values(?)";
    private static final String SQL_SELECT_INTERNATIONAL_NAME_BY_ID=
            "SELECT international_medicine_name_id, international_medicine_name FROM international_medicines_names " +
                    "WHERE international_medicine_name_id = ?";
    @Override
    public boolean create(InternationalMedicineName internationalMedicineName) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTERNATIONAL_NAME)) {
            statement.setString(1, internationalMedicineName.getInternationalName());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding international name exception. " + e.getMessage());
            throw new DaoException("Adding international name exception. ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public List<InternationalMedicineName> findAll() throws DaoException {
        List<InternationalMedicineName> internationalMedicineNames = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_INTERNATIONAL_NAMES);
             ResultSet resultSet = statement.executeQuery()) {
            InternationalNameRowMapper mapper = InternationalNameRowMapper.getInstance();
            Optional<InternationalMedicineName> currentInternationalNameOptional;
            while (resultSet.next()) {
                currentInternationalNameOptional = mapper.mapRow(resultSet);
                currentInternationalNameOptional.ifPresent(internationalMedicineNames::add);
            }
        } catch (SQLException e) {
            LOGGER.error("Find all international medicines names exception. " + e.getMessage());
            throw new DaoException("Find all international medicines names exception. ", e);
        }
        return internationalMedicineNames;
    }

    @Override
    public Optional<InternationalMedicineName> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_INTERNATIONAL_NAME_BY_ID)) {
            statement.setLong(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                InternationalNameRowMapper mapper = InternationalNameRowMapper.getInstance();
                Optional<InternationalMedicineName> internationalMedicineNameOptional;
                if (resultSet.next()) {
                    internationalMedicineNameOptional = mapper.mapRow(resultSet);
                } else {
                    internationalMedicineNameOptional = Optional.empty();
                }
                return internationalMedicineNameOptional;
            }
        } catch (SQLException e) {
            LOGGER.error("Find international name by id exception. " + e.getMessage());
            throw new DaoException("Find international name by id exception. ", e);
        }
    }

    @Override
    public InternationalMedicineName update(InternationalMedicineName internationalMedicineName) throws DaoException {
        return null;
    }
}
