package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.ManufacturerDao;
import com.epam.pharmacy.model.entity.Manufacturer;
import com.epam.pharmacy.model.mapper.impl.ManufacturerRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Manufacturer dao.
 */
public class ManufacturerDaoImpl extends AbstractDao<Manufacturer> implements ManufacturerDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ONE_UPDATED = 1;
    private static final String SQL_SELECT_ALL_MANUFACTURERS =
            "SELECT manufacturer_id, manufacturer_name, manufacturer_country FROM manufacturers";
    private static final String SQL_INSERT_MANUFACTURER =
            "INSERT INTO manufacturers (manufacturer_name, manufacturer_country) values(?,?)";
    private static final String SQL_SELECT_MANUFACTURER_BY_ID =
            "SELECT manufacturer_id, manufacturer_name, manufacturer_country FROM manufacturers " +
                    "WHERE manufacturer_id = ?";
    private static final String SQL_DELETE_MANUFACTURER_BY_ID = "DELETE FROM manufacturers WHERE manufacturer_id = ?";
    private static final String SQL_UPDATE_MANUFACTURER_BY_ID =
            "UPDATE manufacturers SET manufacturer_name = ?, manufacturer_country = ? WHERE manufacturer_id = ?";
    private static final String SQL_SELECT_MANUFACTURER_BY_NAME = "SELECT manufacturer_id, manufacturer_name, manufacturer_country FROM manufacturers " +
            "WHERE manufacturer_name = ?";

    @Override
    public boolean create(Manufacturer manufacturer) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MANUFACTURER)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding manufacturer exception.", e);
            throw new DaoException("Adding manufacturer exception.", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MANUFACTURER_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Delete manufacturer by id exception. id=" + id, e);
            throw new DaoException("Delete manufacturer by id exception. id=" + id, e);
        }
    }

    @Override
    public List<Manufacturer> findAll() throws DaoException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_MANUFACTURERS);
             ResultSet resultSet = statement.executeQuery()) {
            ManufacturerRowMapper mapper = ManufacturerRowMapper.getInstance();
            Optional<Manufacturer> currentManufacturerOptional;
            while (resultSet.next()) {
                currentManufacturerOptional = mapper.mapRow(resultSet);
                currentManufacturerOptional.ifPresent(manufacturers::add);
            }
        } catch (SQLException e) {
            LOGGER.error("Find all manufacturers exception.", e);
            throw new DaoException("Find all manufacturers exception.", e);
        }
        return manufacturers;
    }

    @Override
    public Optional<Manufacturer> findById(long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MANUFACTURER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                ManufacturerRowMapper mapper = ManufacturerRowMapper.getInstance();
                Optional<Manufacturer> manufacturerOptional;
                if (resultSet.next()) {
                    manufacturerOptional = mapper.mapRow(resultSet);
                } else {
                    manufacturerOptional = Optional.empty();
                }
                return manufacturerOptional;
            }
        } catch (SQLException e) {
            LOGGER.error("Find manufacturer by id exception. ", e);
            throw new DaoException("Find manufacturer by id exception. ", e);
        }
    }

    @Override
    public Optional<Manufacturer> findByName(String name) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MANUFACTURER_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                ManufacturerRowMapper mapper = ManufacturerRowMapper.getInstance();
                Optional<Manufacturer> manufacturerOptional;
                if (resultSet.next()) {
                    manufacturerOptional = mapper.mapRow(resultSet);
                } else {
                    manufacturerOptional = Optional.empty();
                }
                return manufacturerOptional;
            }
        } catch (SQLException e) {
            LOGGER.error("Find manufacturer by name exception. Name=" + name, e);
            throw new DaoException("Find manufacturer by name exception. Name=" + name, e);
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer manufacturer) throws DaoException {
        Optional<Manufacturer> oldManufacturer = findById(manufacturer.getId());
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MANUFACTURER_BY_ID)) {
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            return statement.executeUpdate() == ONE_UPDATED ? oldManufacturer : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Adding manufacturer exception.", e);
            throw new DaoException("Adding manufacturer exception.", e);
        }
    }
}
