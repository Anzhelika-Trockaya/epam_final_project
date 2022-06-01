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
    private LocalDate expectedDate;
    private LocalDate completeDate;
    private long pharmacistId;
    private String check;

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

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    public long getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && customerId == order.customerId && pharmacistId == order.pharmacistId && Objects.equals(paymentDate, order.paymentDate) && Objects.equals(expectedDate, order.expectedDate) && Objects.equals(completeDate, order.completeDate) && Objects.equals(check, order.check);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, paymentDate, expectedDate, completeDate, pharmacistId, check);
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getSimpleName()).
                append("id=").append(id).
                append(", customerId=").append(customerId).
                append(", paymentDate=").append(paymentDate).
                append(", expectedDate=").append(expectedDate).
                append(", completeDate=").append(completeDate).
                append(", pharmacistId=").append(pharmacistId).
                append(", check='").append(check).append('\'').
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

        public Builder buildExpectedDate(LocalDate date) {
            order.setExpectedDate(date);
            return this;
        }

        public Builder buildCompleteDate(LocalDate date) {
            order.setCompleteDate(date);
            return this;
        }

        public Builder buildPharmacistId(long id) {
            order.setPharmacistId(id);
            return this;
        }

        public Builder buildCheck(String check) {
            order.setCheck(check);
            return this;
        }

        public Order build() {
            return order;
        }
    }
}