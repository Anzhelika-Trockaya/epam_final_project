package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.OrderDao;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.mapper.impl.MedicineRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.pharmacy.model.dao.ColumnName.*;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_INSERT_ORDER =
            "INSERT INTO orders (customer_id, order_state) VALUES (?,?)";
    private static final String SQL_SELECT_ORDER_ID_WITHOUT_PAYMENT =
            "SELECT order_id FROM orders WHERE customer_id=? AND order_state='CREATED'";
    private static final String SQL_SELECT_ORDER_MEDICINES_AND_QUANTITY =
            "SELECT order_medicine_quantity, medicine_name, international_name_id, medicine_price, " +
                    "medicine_total_number_of_parts, medicine_parts_amount_in_package, medicine_amount_in_part, " +
                    "form_id, medicine_dosage, medicine_dosage_unit, medicine_ingredients, medicine_need_prescription, " +
                    "manufacturer_id, medicine_instruction, medicine_image_path FROM medicines JOIN orders " +
                    "ON medicines.medicine_id = orders.medicine_id WHERE orders.order_id = ?";//fixme check
    private static final String SQL_UPDATE_ORDER = "";//fixme
    private static final String SQL_INSERT_ORDER_ID_MEDICINE_AND_QUANTITY_INTO_M2M_ORDER_MEDICINE =
            "INSERT INTO m2m_order_medicine (order_id, medicine_id, order_medicine_quantity) values(?,?,?)";
    private static final String SQL_UPDATE_QUANTITY_INTO_M2M_ORDER_MEDICINE =
            "UPDATE m2m_order_medicine SET order_medicine_quantity = ? WHERE order_id=? AND medicine_id=?";
    private static final String SQL_SELECT_ORDER_ID_FROM_ROW_IN_M2M_ORDER =
            "SELECT order_id FROM m2m_order_medicine WHERE order_id=? AND medicine_id=?";
    private static final String SQL_INCREASE_QUANTITY_IN_M2M_ORDER =
            "UPDATE m2m_order_medicine SET order_medicine_quantity = order_medicine_quantity + ? " +
                    "WHERE order_id=? AND medicine_id=?";
    private static final int ONE_UPDATED = 1;
    private static final long NOT_EXISTS_ORDER_ID_VALUE = -1;
    private static final String SQL_SELECT_ORDER_MEDICINES_ID_AND_QUANTITY =
            "SELECT order_medicine_quantity, medicine_id FROM m2m_order_medicine WHERE order_id = ?";

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
    public Optional<Order> update(Order order) throws DaoException {
        return null;
    }

    @Override
    public boolean updateQuantityInOrderPosition(long orderId, long medicineId, int quantity) throws DaoException {
        try (PreparedStatement statement = connection.
                prepareStatement(SQL_UPDATE_QUANTITY_INTO_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setInt(3, quantity);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Updating orders position exception. orderId=" + orderId + " medicineId=" + medicineId, e);
            throw new DaoException("Updating orders position exception. orderId=" + orderId + " medicineId=" + medicineId, e);
        }
    }

    @Override
    public boolean addToCart(long orderId, long medicineId, int quantity) throws DaoException {
        try (PreparedStatement statement = connection.
                prepareStatement(SQL_INSERT_ORDER_ID_MEDICINE_AND_QUANTITY_INTO_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setInt(3, quantity);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding new orders position exception. orderId=" + orderId + " medicineId=" + medicineId, e);
            throw new DaoException("Adding new orders position exception. orderId=" + orderId + " medicineId=" + medicineId, e);
        }
    }

    @Override
    public Map<Long, Integer> findOrderMedicineIdAndQuantity(long orderId) throws DaoException {
        Map<Long, Integer> orderContent = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_MEDICINES_ID_AND_QUANTITY)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                long medicineId;
                int quantity;
                while (resultSet.next()) {
                    medicineId = resultSet.getLong(MEDICINE_ID);
                    quantity = resultSet.getInt(ORDER_MEDICINE_QUANTITY);
                    orderContent.put(medicineId, quantity);
                }
            }
            return orderContent;
        } catch (SQLException e) {
            LOGGER.error("Finding order positions exception. orderId=" + orderId, e);
            throw new DaoException("Finding order positions exception. orderId=" + orderId, e);
        }
    }

    @Override
    public Map<Medicine, Integer> findOrderPositions(long orderId) throws DaoException {
        Map<Medicine, Integer> orderPositions = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_MEDICINES_AND_QUANTITY)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                MedicineRowMapper mapper = MedicineRowMapper.getInstance();
                Optional<Medicine> currentMedicineOptional;
                int quantity;
                while (resultSet.next()) {
                    currentMedicineOptional = mapper.mapRow(resultSet);
                    quantity = resultSet.getInt(ORDER_MEDICINE_QUANTITY);
                    if (currentMedicineOptional.isPresent()) {
                        orderPositions.put(currentMedicineOptional.get(), quantity);
                    }
                }
            }
            return orderPositions;
        } catch (SQLException e) {
            LOGGER.error("Finding order positions exception. orderId=" + orderId, e);
            throw new DaoException("Finding order positions exception. orderId=" + orderId, e);
        }
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
            LOGGER.error("Finding order without payment exception. CustomerId=" + customerId, e);
            throw new DaoException("Finding order without payment exception. CustomerId=" + customerId, e);
        }
    }

    @Override
    public boolean createEmptyOrder(long customerId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER)) {
            statement.setLong(1, customerId);
            statement.setString(2, Order.State.CREATED.name());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding new order exception.", e);
            throw new DaoException("Adding new order exception.", e);
        }
    }

    @Override
    public boolean existsPositionInCart(long orderId, long medicineId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_ID_FROM_ROW_IN_M2M_ORDER)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            LOGGER.error("Check existing position in cart exception. orderId=" + orderId + " medicineId=" + medicineId, e);
            throw new DaoException("Check existing position in cart exception. orderId=" + orderId + " medicineId=" + medicineId, e);
        }
    }

    @Override
    public boolean increaseMedicineQuantityInOrder(long orderId, long medicineId, int quantity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INCREASE_QUANTITY_IN_M2M_ORDER)) {
            statement.setInt(1, quantity);
            statement.setLong(2, orderId);
            statement.setLong(3, medicineId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Increasing medicine quantity in order exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " quantity=" + quantity, e);
            throw new DaoException("Increasing medicine quantity in order exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " quantity=" + quantity, e);
        }
    }
}
