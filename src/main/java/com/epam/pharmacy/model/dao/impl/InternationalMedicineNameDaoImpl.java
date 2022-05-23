package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.InternationalMedicineNameDao;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InternationalMedicineNameDaoImpl extends AbstractDao<InternationalMedicineName> implements InternationalMedicineNameDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SQL_INSERT_INTERNATIONAL_NAME =
            "INSERT INTO international_medicines_names (international_medicine_name) values(?)";
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
        return null;
    }

    @Override
    public Optional<InternationalMedicineName> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public InternationalMedicineName update(InternationalMedicineName internationalMedicineName) throws DaoException {
        return null;
    }
}
