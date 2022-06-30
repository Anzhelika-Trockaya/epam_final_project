package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.AbstractDao;
import com.epam.pharmacy.model.dao.ColumnName;
import com.epam.pharmacy.model.dao.OrderDao;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.OrderPosition;
import com.epam.pharmacy.model.mapper.impl.OrderPositionRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.pharmacy.model.dao.ColumnName.*;

public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_INSERT_NEW_ORDER =
            "INSERT INTO orders (customer_id, order_state) VALUES (?,?)";
    private static final String SQL_SELECT_ORDER_ID_WITH_CUSTOMER_ID_WITH_CREATED_STATE =
            "SELECT order_id FROM orders WHERE customer_id = ? AND order_state = 'CREATED'";
    private static final String SQL_SELECT_ORDER_MEDICINES_ID_AND_QUANTITY =
            "SELECT SUM(order_medicine_quantity) AS order_medicine_quantity, medicine_id " +
                    "FROM m2m_order_medicine WHERE order_id = ? GROUP BY medicine_id";
    private static final String SQL_SELECT_ORDER_POSITIONS =
            "SELECT order_id, medicine_id, order_medicine_quantity, prescription_id, order_medicine_price " +
                    "FROM m2m_order_medicine WHERE order_id = ?";
    private static final String SQL_UPDATE_ORDER = "";//fixme
    private static final String SQL_INSERT_ORDER_ID_MEDICINE_ID_PRESCRIPTION_ID_QUANTITY_INTO_M2M_ORDER_MEDICINE =
            "INSERT INTO m2m_order_medicine (order_id, medicine_id, prescription_id, order_medicine_quantity) " +
                    "VALUES(?,?,?,?)";
    private static final String SQL_INSERT_ORDER_ID_MEDICINE_ID_QUANTITY_INTO_M2M_ORDER_MEDICINE =
            "INSERT INTO m2m_order_medicine (order_id, medicine_id, order_medicine_quantity) VALUES(?,?,?)";
    private static final String SQL_SELECT_ORDER_ID_FROM_ROW_IN_M2M_ORDER_MEDICINE =
            "SELECT order_id FROM m2m_order_medicine WHERE order_id = ? AND medicine_id = ? AND prescription_id = ?";
    private static final String SQL_SELECT_NUMBER_WITH_PRESCRIPTION_ID =
            "SELECT SUM(m2m.order_medicine_quantity * med.medicine_number_in_package) AS number " +
                    "FROM m2m_order_medicine m2m JOIN medicines med ON m2m.medicine_id = med.medicine_id " +
                    "WHERE m2m.order_id = ? AND m2m.prescription_id = ?";
    private static final String SQL_SELECT_NUMBER_WITH_PRESCRIPTION_ID_EXCEPT_CURRENT_POSITION =
            "SELECT SUM(m2m.order_medicine_quantity * med.medicine_number_in_package)-" +
                    "(SELECT m2m.order_medicine_quantity * med.medicine_number_in_package " +
                    "FROM m2m_order_medicine m2m JOIN medicines med ON m2m.medicine_id = med.medicine_id " +
                    "WHERE m2m.order_id = ? AND m2m.medicine_id = ? AND m2m.prescription_id = ?) AS number " +
                    "FROM m2m_order_medicine m2m JOIN medicines med ON m2m.medicine_id = med.medicine_id " +
                    "WHERE m2m.order_id = ? AND m2m.prescription_id = ?";
    private static final String SQL_SELECT_MEDICINE_QUANTITY_IN_ORDER_EXCEPT_CURRENT_POSITION =
            "SELECT SUM(order_medicine_quantity)-(" +
                    "SELECT order_medicine_quantity FROM m2m_order_medicine " +
                    "WHERE order_id = ? AND medicine_id = ? AND prescription_id = ?) AS quantity " +
                    "FROM m2m_order_medicine WHERE order_id = ? AND medicine_id = ?";
    private static final String SQL_SELECT_MEDICINE_QUANTITY_IN_ORDER =
            "SELECT SUM(order_medicine_quantity) AS quantity " +
                    "FROM m2m_order_medicine WHERE order_id = ? AND medicine_id = ?";
    private static final String SQL_INCREASE_QUANTITY_IN_ROW_IN_M2M_ORDER_MEDICINE =
            "UPDATE m2m_order_medicine SET order_medicine_quantity = order_medicine_quantity + ? " +
                    "WHERE order_id = ? AND medicine_id = ? AND prescription_id = ?";
    private static final String SQL_UPDATE_QUANTITY_INTO_M2M_ORDER_MEDICINE =
            "UPDATE m2m_order_medicine SET order_medicine_quantity = ? " +
                    "WHERE order_id = ? AND medicine_id = ? AND prescription_id = ?";
    private static final String SQL_EXISTS_PRESCRIPTION_ID =
            "SELECT EXISTS (SELECT order_id FROM m2m_order_medicine WHERE prescription_id = ?)";
    private static final String SQL_DELETE_ROW_FROM_M2M_ORDER_MEDICINE =
            "DELETE FROM m2m_order_medicine WHERE order_id = ? AND medicine_id = ? AND prescription_id = ?";
    private static final String SQL_DELETE_ROW_WITH_ORDER_ID_FROM_M2M_ORDER_MEDICINE =
            "DELETE FROM m2m_order_medicine WHERE order_id = ?";
    private static final int ONE_UPDATED = 1;
    private static final long NOT_EXISTS_ORDER_ID_VALUE = -1;
    private static final int POSITION_NUMBER_IF_NOT_EXISTS_ORDER = 0;

    @Override
    public boolean create(Order order) throws DaoException {
        return false;//fixme??????
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
    public boolean addPositionToOrder(long orderId,
                                      long medicineId,
                                      int quantity,
                                      long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.
                prepareStatement(SQL_INSERT_ORDER_ID_MEDICINE_ID_PRESCRIPTION_ID_QUANTITY_INTO_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setLong(3, prescriptionId);
            statement.setInt(4, quantity);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding new orders position exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Adding new orders position exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public boolean addPositionToOrder(long orderId, long medicineId, int quantity) throws DaoException {
        try (PreparedStatement statement = connection.
                prepareStatement(SQL_INSERT_ORDER_ID_MEDICINE_ID_QUANTITY_INTO_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setInt(3, quantity);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding new orders position exception. orderId=" + orderId +
                    " medicineId=" + medicineId, e);
            throw new DaoException("Adding new orders position exception. orderId=" + orderId +
                    " medicineId=" + medicineId, e);
        }
    }

    @Override
    public Map<Long, Integer> findMedicineIdWithQuantityInOrder(long orderId) throws DaoException {
        Map<Long, Integer> orderPositions = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_MEDICINES_ID_AND_QUANTITY)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                int quantity;
                long medicineId;
                while (resultSet.next()) {
                    medicineId = resultSet.getLong(MEDICINE_ID);
                    quantity = resultSet.getInt(ORDER_MEDICINE_QUANTITY);
                    orderPositions.put(medicineId, quantity);
                }
            }
            return orderPositions;
        } catch (SQLException e) {
            LOGGER.error("Finding medicines in order exception. orderId=" + orderId, e);
            throw new DaoException("Finding medicines in order exception. orderId=" + orderId, e);
        }
    }

    @Override
    public Set<OrderPosition> findOrderPositions(long orderId) throws DaoException {
        Set<OrderPosition> positions = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_POSITIONS)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                OrderPositionRowMapper mapper = OrderPositionRowMapper.getInstance();
                Optional<OrderPosition> currentPositionOptional;
                while (resultSet.next()) {
                    currentPositionOptional = mapper.mapRow(resultSet);
                    currentPositionOptional.ifPresent(positions::add);
                }
            }
            return positions;
        } catch (SQLException e) {
            LOGGER.error("Finding order positions exception. orderId=" + orderId, e);
            throw new DaoException("Finding order positions exception. orderId=" + orderId, e);
        }
    }

    @Override
    public long getOrderIdWithoutPayment(long customerId) throws DaoException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ORDER_ID_WITH_CUSTOMER_ID_WITH_CREATED_STATE)) {
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_ORDER)) {
            statement.setLong(1, customerId);
            statement.setString(2, Order.State.CREATED.name());
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Adding new order exception.", e);
            throw new DaoException("Adding new order exception.", e);
        }
    }

    @Override
    public boolean existsPositionInOrder(long orderId, long medicineId, long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_ID_FROM_ROW_IN_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setLong(3, prescriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            LOGGER.error("Check existing position in cart exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Check existing position in cart exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public boolean increaseQuantityInOrderPosition(long orderId,
                                                   long medicineId,
                                                   int quantity,
                                                   long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INCREASE_QUANTITY_IN_ROW_IN_M2M_ORDER_MEDICINE)) {
            statement.setInt(1, quantity);
            statement.setLong(2, orderId);
            statement.setLong(3, medicineId);
            statement.setLong(4, prescriptionId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Increasing medicine quantity in order exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " quantity=" + quantity + " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Increasing medicine quantity in order exception. orderId=" + orderId +
                    " medicineId=" + medicineId + " quantity=" + quantity + " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public int findMedicineQuantityInOrder(long orderId, long medicineId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MEDICINE_QUANTITY_IN_ORDER)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(ColumnName.QUANTITY);
                } else {
                    LOGGER.warn("Medicine quantity in order not found. OrderId=" + orderId +
                            " medicineId=" + medicineId);
                    return POSITION_NUMBER_IF_NOT_EXISTS_ORDER;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when find medicine quantity in order. " +
                    "OrderId=" + orderId + " medicineId=" + medicineId, e);
            throw new DaoException("Exception when find medicine quantity in order. " +
                    "OrderId=" + orderId + " medicineId=" + medicineId, e);
        }
    }

    @Override
    public int findMedicineQuantityInOrderExceptCurrentPosition(long orderId,
                                                                long medicineId,
                                                                long prescriptionId) throws DaoException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_MEDICINE_QUANTITY_IN_ORDER_EXCEPT_CURRENT_POSITION)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setLong(3, prescriptionId);
            statement.setLong(4, orderId);
            statement.setLong(5, medicineId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(ColumnName.QUANTITY);
                } else {
                    LOGGER.warn("Medicine quantity in order except current position not found. OrderId=" + orderId +
                            " medicineId=" + medicineId + " prescriptionId=" + prescriptionId);
                    return POSITION_NUMBER_IF_NOT_EXISTS_ORDER;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when find medicine quantity in order except current position. " +
                    "OrderId=" + orderId + " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Exception when find medicine quantity in order except current position. " +
                    "OrderId=" + orderId + " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public int findNumberForPrescriptionInOrder(long orderId, long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_NUMBER_WITH_PRESCRIPTION_ID)) {
            statement.setLong(1, orderId);
            statement.setLong(2, prescriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(ColumnName.NUMBER);
                } else {
                    LOGGER.warn("Order positions not found. OrderId=" + orderId + " prescriptionId=" + prescriptionId);
                    return POSITION_NUMBER_IF_NOT_EXISTS_ORDER;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when find prescription number in order. OrderId=" + orderId +
                    " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Exception when find prescription number in order. OrderId=" + orderId +
                    " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public int findNumberForPrescriptionInOrderExceptCurrentPosition(long orderId,
                                                                     long medicineId,
                                                                     long prescriptionId) throws DaoException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_NUMBER_WITH_PRESCRIPTION_ID_EXCEPT_CURRENT_POSITION)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setLong(3, prescriptionId);
            statement.setLong(4, orderId);
            statement.setLong(5, prescriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(ColumnName.NUMBER);
                } else {
                    LOGGER.warn("Order position not found. OrderId=" + orderId +
                            " medicineId=" + medicineId + " prescriptionId=" + prescriptionId);
                    return POSITION_NUMBER_IF_NOT_EXISTS_ORDER;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when find prescription number in order. OrderId=" + orderId +
                    " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Exception when find prescription number in order. OrderId=" + orderId +
                    " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public boolean existsPrescriptionInOrders(long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_EXISTS_PRESCRIPTION_ID)) {
            statement.setLong(1, prescriptionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception when check existing prescription id in orders", e);
            throw new DaoException("Exception when check existing prescription id in orders", e);
        }
    }

    @Override
    public boolean deletePositionFromOrder(long orderId, long medicineId, long prescriptionId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ROW_FROM_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            statement.setLong(2, medicineId);
            statement.setLong(3, prescriptionId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when delete order position. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
            throw new DaoException("Exception when delete order position. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId, e);
        }
    }

    @Override
    public boolean deleteAllPositionsFromOrder(long orderId) throws DaoException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_DELETE_ROW_WITH_ORDER_ID_FROM_M2M_ORDER_MEDICINE)) {
            statement.setLong(1, orderId);
            return statement.executeUpdate() >= ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when delete rows with order_id. orderId=" + orderId, e);
            throw new DaoException("Exception when delete rows with order_id. orderId=" + orderId, e);
        }
    }

    @Override
    public boolean changePositionQuantityInOrder(long orderId,
                                                 long medicineId,
                                                 long prescriptionId,
                                                 int quantity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_QUANTITY_INTO_M2M_ORDER_MEDICINE)) {
            statement.setInt(1, quantity);
            statement.setLong(2, orderId);
            statement.setLong(3, medicineId);
            statement.setLong(4, prescriptionId);
            return statement.executeUpdate() == ONE_UPDATED;
        } catch (SQLException e) {
            LOGGER.error("Exception when change quantity in order position. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId + " quantity=" + quantity, e);
            throw new DaoException("Exception when change quantity in order position. orderId=" + orderId +
                    " medicineId=" + medicineId + " prescriptionId=" + prescriptionId + " quantity=" + quantity, e);
        }
    }
}
