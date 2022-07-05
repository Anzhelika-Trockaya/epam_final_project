package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The type Prescription.
 */
public class Prescription extends AbstractEntity implements Serializable {
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

    /**
     * Instantiates a new Prescription.
     */
    public Prescription() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new Prescription.
     *
     * @param id the id
     */
    public Prescription(long id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
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
     * Gets doctor id.
     *
     * @return the doctor id
     */
    public long getDoctorId() {
        return doctorId;
    }

    /**
     * Sets doctor id.
     *
     * @param doctorId the doctor id
     */
    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets international name id.
     *
     * @return the international name id
     */
    public long getInternationalNameId() {
        return internationalNameId;
    }

    /**
     * Sets international name id.
     *
     * @param internationalNameId the international name id
     */
    public void setInternationalNameId(long internationalNameId) {
        this.internationalNameId = internationalNameId;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public FormUnit getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(FormUnit unit) {
        this.unit = unit;
    }

    /**
     * Gets dosage.
     *
     * @return the dosage
     */
    public int getDosage() {
        return dosage;
    }

    /**
     * Sets dosage.
     *
     * @param dosage the dosage
     */
    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets dosage unit.
     *
     * @return the dosage unit
     */
    public DosageUnit getDosageUnit() {
        return dosageUnit;
    }

    /**
     * Sets dosage unit.
     *
     * @param dosageUnit the dosage unit
     */
    public void setDosageUnit(DosageUnit dosageUnit) {
        this.dosageUnit = dosageUnit;
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
     * Gets sold quantity.
     *
     * @return the sold quantity
     */
    public int getSoldQuantity() {
        return soldQuantity;
    }

    /**
     * Sets sold quantity.
     *
     * @param soldQuantity the sold quantity
     */
    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    /**
     * Gets expiration date.
     *
     * @return the expiration date
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets expiration date.
     *
     * @param expirationDate the expiration date
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Is need renewal boolean.
     *
     * @return the boolean
     */
    public boolean isNeedRenewal() {
        return needRenewal;
    }

    /**
     * Sets need renewal.
     *
     * @param needRenewal the need renewal
     */
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

    /**
     * The type Builder.
     */
    public static class Builder {
        private final Prescription prescription;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            prescription = new Prescription();
        }

        /**
         * Instantiates a new Builder.
         *
         * @param id the id
         */
        public Builder(long id) {
            prescription = new Prescription(id);
        }

        /**
         * Builds customer id.
         *
         * @param id the id
         * @return the builder
         */
        public Builder buildCustomerId(long id) {
            prescription.customerId = id;
            return this;
        }

        /**
         * Builds doctor id.
         *
         * @param id the id
         * @return the builder
         */
        public Builder buildDoctorId(long id) {
            prescription.doctorId = id;
            return this;
        }

        /**
         * Builds international name id.
         *
         * @param id the id
         * @return the builder
         */
        public Builder buildInternationalNameId(long id) {
            prescription.internationalNameId = id;
            return this;
        }

        /**
         * Builds prescription form unit.
         *
         * @param unit the form unit.
         * @return the builder.
         */
        public Builder buildUnit(FormUnit unit) {
            prescription.unit = unit;
            return this;
        }

        /**
         * Builds dosage.
         *
         * @param dosage the dosage.
         * @return the builder.
         */
        public Builder buildDosage(int dosage) {
            prescription.dosage = dosage;
            return this;
        }

        /**
         * Builds prescription dosage unit.
         *
         * @param unit the unit
         * @return the builder
         */
        public Builder buildDosageUnit(DosageUnit unit) {
            prescription.dosageUnit = unit;
            return this;
        }

        /**
         * Builds prescription quantity.
         *
         * @param quantity the quantity
         * @return the builder
         */
        public Builder buildQuantity(int quantity) {
            prescription.quantity = quantity;
            return this;
        }

        /**
         * Builds prescription sold quantity.
         *
         * @param quantity the sold quantity
         * @return the builder
         */
        public Builder buildSoldQuantity(int quantity) {
            prescription.soldQuantity = quantity;
            return this;
        }

        /**
         * Builds prescription expiration date.
         *
         * @param date the date
         * @return the builder
         */
        public Builder buildExpirationDate(LocalDate date) {
            prescription.expirationDate = date;
            return this;
        }

        /**
         * Builds prescription need renewal.
         *
         * @param value {@code true} if needs renewal
         * @return the builder
         */
        public Builder buildNeedRenewal(boolean value) {
            prescription.needRenewal = value;
            return this;
        }

        /**
         * Builds new Prescription.
         *
         * @return the prescription
         */
        public Prescription build() {
            return prescription;
        }
    }
}
