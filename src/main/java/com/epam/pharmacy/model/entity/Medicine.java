package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Medicine.
 */
public class Medicine extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 6279244349374001815L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String name;
    private long internationalNameId;
    private BigDecimal price;
    private int totalPackages;
    private int numberInPackage;
    private long formId;
    private int dosage;
    private DosageUnit dosageUnit;
    private boolean needPrescription;
    private long manufacturerId;
    private String imagePath;

    /**
     * Instantiates a new Medicine with default id value.
     */
    public Medicine() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new Medicine.
     *
     * @param id medicine id
     */
    public Medicine(long id) {
        this.id = id;
    }

    /**
     * Gets medicine id.
     *
     * @return the medicine id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets medicine name.
     *
     * @return the medicine name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets medicine international name id.
     *
     * @return the medicine international name id
     */
    public long getInternationalNameId() {
        return internationalNameId;
    }

    /**
     * Sets medicine international name id.
     *
     * @param internationalNameId the medicine international name id
     */
    public void setInternationalNameId(long internationalNameId) {
        this.internationalNameId = internationalNameId;
    }

    /**
     * Gets medicine price.
     *
     * @return the medicine price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets medicine price.
     *
     * @param price the medicine price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets medicine total packages quantity.
     *
     * @return the medicine total packages quantity
     */
    public int getTotalPackages() {
        return totalPackages;
    }

    /**
     * Sets medicine total packages quantity.
     *
     * @param totalPackages the medicine total packages quantity
     */
    public void setTotalPackages(int totalPackages) {
        this.totalPackages = totalPackages;
    }

    /**
     * Gets medicine number in package.
     *
     * @return the medicine number in package
     */
    public int getNumberInPackage() {
        return numberInPackage;
    }

    /**
     * Sets medicine number in package.
     *
     * @param numberInPackage the medicine number in package
     */
    public void setNumberInPackage(int numberInPackage) {
        this.numberInPackage = numberInPackage;
    }

    /**
     * Gets medicine form id.
     *
     * @return the medicine form id
     */
    public long getFormId() {
        return formId;
    }

    /**
     * Sets medicine form id.
     *
     * @param formId the medicine form id
     */
    public void setFormId(long formId) {
        this.formId = formId;
    }

    /**
     * Gets medicine dosage value.
     *
     * @return the medicine dosage value
     */
    public int getDosage() {
        return dosage;
    }

    /**
     * Sets medicine dosage value.
     *
     * @param dosage the medicine dosage value
     */
    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets medicine dosage unit.
     *
     * @return the medicine dosage unit
     */
    public DosageUnit getDosageUnit() {
        return dosageUnit;
    }

    /**
     * Sets medicine dosage unit.
     *
     * @param dosageUnit the medicine dosage unit
     */
    public void setDosageUnit(DosageUnit dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    /**
     * Gets if the medicine needs prescription.
     *
     * @return {@code true} if medicine needs prescription
     */
    public boolean needPrescription() {
        return needPrescription;
    }

    /**
     * Sets medicine needs prescription.
     *
     * @param needPrescription {@code true} if medicine needs prescription
     */
    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    /**
     * Gets medicine manufacturer id.
     *
     * @return the medicine manufacturer id
     */
    public long getManufacturerId() {
        return manufacturerId;
    }

    /**
     * Sets medicine manufacturer id.
     *
     * @param manufacturerId the medicine manufacturer id
     */
    public void setManufacturerId(long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /**
     * Gets medicine image path.
     *
     * @return the medicine image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets medicine image path.
     *
     * @param imagePath the medicine image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return id == medicine.id && internationalNameId == medicine.internationalNameId &&
                totalPackages == medicine.totalPackages && numberInPackage == medicine.numberInPackage &&
                formId == medicine.formId && dosage == medicine.dosage &&
                needPrescription == medicine.needPrescription && manufacturerId == medicine.manufacturerId &&
                Objects.equals(name, medicine.name) && Objects.equals(price, medicine.price) &&
                dosageUnit == medicine.dosageUnit && Objects.equals(imagePath, medicine.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, internationalNameId, price, totalPackages, numberInPackage, formId, dosage,
                dosageUnit, needPrescription, manufacturerId, imagePath);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", internationalNameId=" + internationalNameId +
                ", price=" + price +
                ", totalPackages=" + totalPackages +
                ", numberInPackage=" + numberInPackage +
                ", formId=" + formId +
                ", dosage=" + dosage +
                ", dosageUnit=" + dosageUnit +
                ", needPrescription=" + needPrescription +
                ", manufacturerId=" + manufacturerId +
                ", imagePath=" + imagePath +
                '}';
    }

    /**
     * The type Builder. Used for building new Medicine object.
     */
    public static class Builder {
        private final Medicine medicine;

        /**
         * Instantiates a new medicine Builder.
         */
        public Builder() {
            medicine = new Medicine();
        }

        /**
         * Instantiates a new medicine Builder.
         *
         * @param id medicine id
         */
        public Builder(long id) {
            medicine = new Medicine(id);
        }

        /**
         * Builds the medicine name.
         *
         * @param name the medicine name.
         * @return the builder.
         */
        public Builder buildName(String name) {
            medicine.setName(name);
            return this;
        }

        /**
         * Builds the medicine international name id.
         *
         * @param id the medicine international name id
         * @return the builder.
         */
        public Builder buildInternationalNameId(long id) {
            medicine.setInternationalNameId(id);
            return this;
        }

        /**
         * Builds the medicine price.
         *
         * @param price the medicine price.
         * @return the builder.
         */
        public Builder buildPrice(BigDecimal price) {
            medicine.setPrice(price);
            return this;
        }

        /**
         * Builds the medicine total package quantity.
         *
         * @param quantity the medicine total package quantity.
         * @return the builder.
         */
        public Builder buildTotalPackages(int quantity) {
            medicine.setTotalPackages(quantity);
            return this;
        }

        /**
         * Builds the medicine number in package.
         *
         * @param amount the medicine number in package.
         * @return the builder.
         */
        public Builder buildNumberInPackage(int amount) {
            medicine.setNumberInPackage(amount);
            return this;
        }

        /**
         * Builds the medicine form id.
         *
         * @param id the medicine form id.
         * @return the builder.
         */
        public Builder buildFormId(long id) {
            medicine.setFormId(id);
            return this;
        }

        /**
         * Builds the medicine dosage.
         *
         * @param dosage the medicine dosage.
         * @return the builder.
         */
        public Builder buildDosage(int dosage) {
            medicine.setDosage(dosage);
            return this;
        }

        /**
         * Builds the medicine dosage unit.
         *
         * @param unit the medicine dosage unit.
         * @return the builder.
         */
        public Builder buildDosageUnit(DosageUnit unit) {
            medicine.setDosageUnit(unit);
            return this;
        }

        /**
         * Builds if the medicine need prescription.
         *
         * @param needPrescription {@code true} if the medicine need prescription.
         * @return the builder.
         */
        public Builder buildNeedPrescription(boolean needPrescription) {
            medicine.setNeedPrescription(needPrescription);
            return this;
        }

        /**
         * Builds the medicine manufacturer id.
         *
         * @param id the medicine manufacturer id.
         * @return the builder.
         */
        public Builder buildManufacturerId(long id) {
            medicine.setManufacturerId(id);
            return this;
        }

        /**
         * Builds the medicine image path.
         *
         * @param path the medicine image path.
         * @return the builder.
         */
        public Builder buildImagePath(String path) {
            medicine.setImagePath(path);
            return this;
        }

        /**
         * Builds new Medicine object.
         *
         * @return new Medicine object.
         */
        public Medicine build() {
            return medicine;
        }
    }
}
