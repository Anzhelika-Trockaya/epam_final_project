package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.OrderDao;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.ORDER_ID;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_INSERT_ORDER =
            "INSERT INTO orders (customer_id) values(?)";
    private static final String SQL_SELECT_ORDER_ID_WITHOUT_PAYMENT =
            "SELECT order_id from orders WHERE customer_id=?&order_payment_date=NULL";
    private static final String SQL_SELECT_ORDERS_MEDICINES =
            "SELECT medicine from orders WHERE customer_id=?&order_payment_date=NULL";
    private static final String SQL_UPDATE_ORDER =
            "INSERT INTO orders (customer_id, order_payment_date, order_expected_date, order_complete_date, pharmacist_id) " +
                    "values(?,?,?,?,?)";
    private static final int ONE_UPDATED = 1;
    private static final long NOT_EXISTS_ORDER_ID_VALUE = -1;

    @Override
    public boolean create(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Order update(Order order) throws DaoException {
        return null;
    }

    @Override
    public List<Medicine> findOrdersMedicines(long orderId){

    }

    @Override
    public long getOrderIdWithoutPayment(long customerId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_ID_WITHOUT_PAYMENT)) {
            statement.setLong(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                long id;
                if (resultSet.next()) {
                    id = resultSet.getLong(ORDER_ID);
                } else {
                    id = NOT_EXISTS_ORDER_ID_VALUE;
                }
                return id;
            }
        } catch (SQLException e) {
            LOGGER.error("Finding order without payment exception. CustomerId=" + customerId + e.getMessage());
            throw new DaoException("Finding order without payment exception. CustomerId=" + customerId, e);
        }
    }

    @Override
    public boolean createEmptyOrder(long customerId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER)) {
            statement.setLong(1, customerId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding new order exception. " + e.getMessage());
            throw new DaoException("Adding new order exception. ", e);
        }
    }
}
