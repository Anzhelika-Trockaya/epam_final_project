package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class OrderPosition extends CustomEntity implements Serializable {
    private static final long serialVersionUID = 5470581933168083948L;
    private long orderId;
    private long medicineId;
    private long prescriptionId;
    private int quantity;
    private BigDecimal price;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPosition that = (OrderPosition) o;
        return orderId == that.orderId && medicineId == that.medicineId && prescriptionId == that.prescriptionId &&
                quantity == that.quantity && Objects.equals(that.price, price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, medicineId, prescriptionId, quantity, price);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{orderId=").append(orderId);
        sb.append(", medicineId=").append(medicineId);
        sb.append(", prescriptionId=").append(prescriptionId);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private final OrderPosition orderPosition;

        public Builder() {
            orderPosition = new OrderPosition();
        }

        public Builder buildOrderId(long orderId) {
            orderPosition.orderId = orderId;
            return this;
        }

        public Builder buildMedicineId(long medicineId) {
            orderPosition.medicineId = medicineId;
            return this;
        }

        public Builder buildPrescriptionId(long prescriptionId) {
            orderPosition.prescriptionId = prescriptionId;
            return this;
        }

        public Builder buildQuantity(int quantity) {
            orderPosition.quantity = quantity;
            return this;
        }

        public Builder buildPrice(BigDecimal price) {
            orderPosition.price = price;
            return this;
        }

        public OrderPosition build() {
            return orderPosition;
        }
    }
}
