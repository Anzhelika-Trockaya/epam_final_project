package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Order position.
 */
public class OrderPosition extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 5470581933168083948L;
    private long orderId;
    private long medicineId;
    private long prescriptionId;
    private int quantity;
    private BigDecimal price;

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets medicine id.
     *
     * @return the medicine id
     */
    public long getMedicineId() {
        return medicineId;
    }

    /**
     * Sets medicine id.
     *
     * @param medicineId the medicine id
     */
    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * Gets prescription id.
     *
     * @return the prescription id
     */
    public long getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Sets prescription id.
     *
     * @param prescriptionId the prescription id
     */
    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
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

    /**
     * The type Builder.
     */
    public static class Builder {
        private final OrderPosition orderPosition;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            orderPosition = new OrderPosition();
        }

        /**
         * Builds order id.
         *
         * @param orderId the order id
         * @return the builder
         */
        public Builder buildOrderId(long orderId) {
            orderPosition.orderId = orderId;
            return this;
        }

        /**
         * Builds medicine id.
         *
         * @param medicineId the medicine id
         * @return the builder
         */
        public Builder buildMedicineId(long medicineId) {
            orderPosition.medicineId = medicineId;
            return this;
        }

        /**
         * Builds prescription id.
         *
         * @param prescriptionId the prescription id
         * @return the builder
         */
        public Builder buildPrescriptionId(long prescriptionId) {
            orderPosition.prescriptionId = prescriptionId;
            return this;
        }

        /**
         * Builds quantity.
         *
         * @param quantity the quantity
         * @return the builder
         */
        public Builder buildQuantity(int quantity) {
            orderPosition.quantity = quantity;
            return this;
        }

        /**
         * Builds price.
         *
         * @param price the price
         * @return the builder
         */
        public Builder buildPrice(BigDecimal price) {
            orderPosition.price = price;
            return this;
        }

        /**
         * Builds new Order position.
         *
         * @return the order position
         */
        public OrderPosition build() {
            return orderPosition;
        }
    }
}
