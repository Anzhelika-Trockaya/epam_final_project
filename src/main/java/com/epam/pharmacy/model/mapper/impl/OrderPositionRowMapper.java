package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.ColumnName;
import com.epam.pharmacy.model.entity.OrderPosition;
import com.epam.pharmacy.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.epam.pharmacy.model.dao.ColumnName.*;

public class OrderPositionRowMapper implements CustomRowMapper<OrderPosition> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static OrderPositionRowMapper instance;

    private OrderPositionRowMapper() {
    }

    public static OrderPositionRowMapper getInstance() {
        if (instance == null) {
            instance = new OrderPositionRowMapper();
        }
        return instance;
    }

    @Override
    public Optional<OrderPosition> mapRow(ResultSet resultSet) {
        Optional<OrderPosition> optionalPosition;
        try {
            long orderId = resultSet.getLong(ORDER_ID);
            long medicineId = resultSet.getLong(MEDICINE_ID);
            long prescriptionId = resultSet.getLong(PRESCRIPTION_ID);
            int quantity = resultSet.getInt(ORDER_MEDICINE_QUANTITY);
            BigDecimal price = resultSet.getBigDecimal(ColumnName.ORDER_MEDICINE_PRICE);
            OrderPosition position = new OrderPosition.Builder().
                    buildOrderId(orderId).
                    buildMedicineId(medicineId).
                    buildPrescriptionId(prescriptionId).
                    buildQuantity(quantity).
                    buildPrice(price).
                    build();
            optionalPosition = Optional.of(position);
        } catch (SQLException e) {
            LOGGER.warn("Exception when map order position row. ", e);
            optionalPosition = Optional.empty();
        }
        return optionalPosition;
    }
}
