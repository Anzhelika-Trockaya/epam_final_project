package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Order extends CustomEntity implements Serializable {
    private static final long serialVersionUID = -2454843638994676049L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private long customerId;
    private LocalDate paymentDate;
    private State state;
    private long pharmacistId;

    public enum State {
        CREATED,
        PAID,
        PROCESSED,
        COMPLETED
    }

    public Order() {
        id = DEFAULT_ID;
    }

    public Order(long id) {
        this.id = id;
    }

    public Order(long id, long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public long getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && customerId == order.customerId && pharmacistId == order.pharmacistId &&
                state == order.state && Objects.equals(paymentDate, order.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, state, paymentDate, pharmacistId);
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getSimpleName()).
                append("id=").append(id).
                append(", customerId=").append(customerId).
                append(", state=").append(state).
                append(", paymentDate=").append(paymentDate).
                append(", pharmacistId=").append(pharmacistId).
                append('}').toString();
    }

    public static class Builder {
        private final Order order;

        public Builder() {
            order = new Order();
        }

        public Builder(long id) {
            order = new Order(id);
        }

        public Builder(long id, long customerId) {
            order = new Order(id, customerId);
        }

        public Builder buildCustomerId(long customerId) {
            order.setCustomerId(customerId);
            return this;
        }

        public Builder buildPaymentDate(LocalDate date) {
            order.setPaymentDate(date);
            return this;
        }

        public Builder buildPharmacistId(State state) {
            order.setState(state);
            return this;
        }

        public Builder buildPharmacistId(long id) {
            order.setPharmacistId(id);
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
