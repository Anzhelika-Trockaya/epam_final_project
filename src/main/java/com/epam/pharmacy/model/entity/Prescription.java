package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Prescription extends CustomEntity implements Serializable {
    private static final long serialVersionUID = -6875619486502062065L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private long customerId;
    private long doctorId;
    private long internationalNameId;
    private FormUnit unit;
    private int dosage;
    private DosageUnit dosageUnit;
    private int quantity;
    private int soldQuantity;
    private LocalDate expirationDate;
    private boolean needRenewal;

    public Prescription() {
        id = DEFAULT_ID;
    }

    public Prescription(long id) {
        this.id = id;
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

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getInternationalNameId() {
        return internationalNameId;
    }

    public void setInternationalNameId(long internationalNameId) {
        this.internationalNameId = internationalNameId;
    }

    public FormUnit getUnit() {
        return unit;
    }

    public void setUnit(FormUnit unit) {
        this.unit = unit;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public DosageUnit getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(DosageUnit dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isNeedRenewal() {
        return needRenewal;
    }

    public void setNeedRenewal(boolean needRenewal) {
        this.needRenewal = needRenewal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return id == that.id && customerId == that.customerId && doctorId == that.doctorId &&
                internationalNameId == that.internationalNameId && unit == that.unit && dosage == that.dosage &&
                quantity == that.quantity && soldQuantity == that.soldQuantity && needRenewal == that.needRenewal &&
                dosageUnit == that.dosageUnit && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, doctorId, internationalNameId, unit, dosage, dosageUnit, quantity,
                soldQuantity, expirationDate, needRenewal);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", internationalNameId=").append(internationalNameId);
        sb.append(", unit=").append(unit);
        sb.append(", dosage=").append(dosage);
        sb.append(", dosageUnit=").append(dosageUnit);
        sb.append(", quantity=").append(quantity);
        sb.append(", soldQuantity=").append(soldQuantity);
        sb.append(", expirationDate=").append(expirationDate);
        sb.append(", needRenewal=").append(needRenewal);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private final Prescription prescription;

        public Builder() {
            prescription = new Prescription();
        }

        public Builder(long id) {
            prescription = new Prescription(id);
        }

        public Builder buildCustomerId(long id) {
            prescription.customerId = id;
            return this;
        }

        public Builder buildDoctorId(long id) {
            prescription.doctorId = id;
            return this;
        }

        public Builder buildInternationalNameId(long id) {
            prescription.internationalNameId = id;
            return this;
        }

        public Builder buildUnit(FormUnit unit) {
            prescription.unit = unit;
            return this;
        }

        public Builder buildDosage(int dosage) {
            prescription.dosage = dosage;
            return this;
        }

        public Builder buildDosageUnit(DosageUnit unit) {
            prescription.dosageUnit = unit;
            return this;
        }

        public Builder buildQuantity(int quantity) {
            prescription.quantity = quantity;
            return this;
        }

        public Builder buildSoldQuantity(int quantity) {
            prescription.soldQuantity = quantity;
            return this;
        }

        public Builder buildExpirationDate(LocalDate date) {
            prescription.expirationDate = date;
            return this;
        }

        public Builder buildNeedRenewal(boolean value) {
            prescription.needRenewal = value;
            return this;
        }

        public Prescription build() {
            return prescription;
        }
    }
}
