package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.ColumnName;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.*;

public class OrderRowMapper implements CustomRowMapper<Order> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static OrderRowMapper instance;

    private OrderRowMapper() {
    }

    public static OrderRowMapper getInstance() {
        if (instance == null) {
            instance = new OrderRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<Order> mapRow(ResultSet resultSet) {
        Optional<Order> optionalOrder;
        try {
            long orderId = resultSet.getLong(ORDER_ID);
            long customerId = resultSet.getLong(ColumnName.CUSTOMER_ID);
            long pharmacistId = resultSet.getLong(ColumnName.PHARMACIST_ID);
            String stateString = resultSet.getString(ColumnName.ORDER_STATE);
            Order.State state = Order.State.valueOf(stateString);
            Date date = resultSet.getDate(ColumnName.ORDER_PAYMENT_DATE);
            LocalDate paymentDate = date.toLocalDate();
            BigDecimal totalCost = resultSet.getBigDecimal(ColumnName.ORDER_TOTAL_COST);
            Order.Builder builder = new Order.Builder(orderId);
            Order order = builder.
                    buildCustomerId(customerId).
                    buildPharmacistId(pharmacistId).
                    buildState(state).
                    buildPaymentDate(paymentDate).
                    buildTotalCost(totalCost).
                    build();
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map order row. ", e);
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}
