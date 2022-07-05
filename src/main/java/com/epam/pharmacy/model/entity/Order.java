package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The type Order.
 */
public class Order extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -2454843638994676049L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private long customerId;
    private LocalDate paymentDate;
    private State state;
    private long pharmacistId;
    private BigDecimal totalCost;

    /**
     * The enum State.
     */
    public enum State {
        /**
         * Created state.
         */
        CREATED,
        /**
         * Paid state.
         */
        PAID,
        /**
         * In progress state.
         */
        IN_PROGRESS,
        /**
         * Completed state.
         */
        COMPLETED
    }

    /**
     * Instantiates a new Order.
     */
    public Order() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new Order.
     *
     * @param id the id
     */
    public Order(long id) {
        this.id = id;
    }

    /**
     * Instantiates a new Order.
     *
     * @param id         the id
     * @param customerId the customer id
     */
    public Order(long id, long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets payment date.
     *
     * @return the payment date
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets payment date.
     *
     * @param paymentDate the payment date
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Gets pharmacist id.
     *
     * @return the pharmacist id
     */
    public long getPharmacistId() {
        return pharmacistId;
    }

    /**
     * Sets pharmacist id.
     *
     * @param pharmacistId the pharmacist id
     */
    public void setPharmacistId(long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Gets total cost.
     *
     * @return the total cost
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * Sets total cost.
     *
     * @param totalCost the total cost
     */
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && customerId == order.customerId && pharmacistId == order.pharmacistId &&
                state == order.state && Objects.equals(paymentDate, order.paymentDate) &&
                Objects.equals(totalCost, order.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, state, paymentDate, pharmacistId, totalCost);
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getSimpleName()).
                append("id=").append(id).
                append(", customerId=").append(customerId).
                append(", pharmacistId=").append(pharmacistId).
                append(", state=").append(state).
                append(", paymentDate=").append(paymentDate).
                append(", totalCost=").append(totalCost).
                append('}').toString();
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private final Order order;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            order = new Order();
        }

        /**
         * Instantiates a new Builder.
         *
         * @param id the id
         */
        public Builder(long id) {
            order = new Order(id);
        }

        /**
         * Instantiates a new Builder.
         *
         * @param id         the id
         * @param customerId the customer id
         */
        public Builder(long id, long customerId) {
            order = new Order(id, customerId);
        }

        /**
         * Builds customer id.
         *
         * @param customerId the customer id
         * @return the builder
         */
        public Builder buildCustomerId(long customerId) {
            order.customerId = customerId;
            return this;
        }

        /**
         * Builds payment date.
         *
         * @param date the date
         * @return the builder
         */
        public Builder buildPaymentDate(LocalDate date) {
            order.paymentDate = date;
            return this;
        }

        /**
         * Builds pharmacist id.
         *
         * @param state the state
         * @return the builder
         */
        public Builder buildPharmacistId(State state) {
            order.state = state;
            return this;
        }

        /**
         * Builds pharmacist id.
         *
         * @param id the id
         * @return the builder
         */
        public Builder buildPharmacistId(long id) {
            order.pharmacistId = id;
            return this;
        }

        /**
         * Builds total cost.
         *
         * @param totalCost the total cost
         * @return the builder
         */
        public Builder buildTotalCost(BigDecimal totalCost) {
            order.totalCost = totalCost;
            return this;
        }

        /**
         * Builds state.
         *
         * @param state the state
         * @return the builder
         */
        public Builder buildState(State state) {
            order.state = state;
            return this;
        }

        /**
         * Builds new Order.
         *
         * @return the order
         */
        public Order build() {
            return order;
        }
    }
}
