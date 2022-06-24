package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Medicine extends CustomEntity implements Serializable {
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
    private String ingredients;
    private boolean needPrescription;
    private long manufacturerId;
    private String instruction;
    private String imagePath;

    public Medicine() {
        id = DEFAULT_ID;
    }

    public Medicine(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInternationalNameId() {
        return internationalNameId;
    }

    public void setInternationalNameId(long internationalNameId) {
        this.internationalNameId = internationalNameId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(int totalPackages) {
        this.totalPackages = totalPackages;
    }

    public int getNumberInPackage() {
        return numberInPackage;
    }

    public void setNumberInPackage(int numberInPackage) {
        this.numberInPackage = numberInPackage;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public boolean needPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath=imagePath;
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
                dosageUnit == medicine.dosageUnit && Objects.equals(ingredients, medicine.ingredients) &&
                Objects.equals(instruction, medicine.instruction) && Objects.equals(imagePath, medicine.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, internationalNameId, price, totalPackages, numberInPackage, formId, dosage,
                dosageUnit, ingredients, needPrescription, manufacturerId, instruction, imagePath);
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
                ", ingredients='" + ingredients + '\'' +
                ", needPrescription=" + needPrescription +
                ", manufacturerId=" + manufacturerId +
                ", instruction='" + instruction + '\'' +
                ", imagePath=" + imagePath +
                '}';
    }

    public static class Builder {
        private final Medicine medicine;

        public Builder() {
            medicine = new Medicine();
        }

        public Builder(long id) {
            medicine = new Medicine(id);
        }

        public Builder buildName(String name) {
            medicine.setName(name);
            return this;
        }

        public Builder buildInternationalNameId(long id) {
            medicine.setInternationalNameId(id);
            return this;
        }

        public Builder buildPrice(BigDecimal price) {
            medicine.setPrice(price);
            return this;
        }

        public Builder buildTotalPackages(int quantity) {
            medicine.setTotalPackages(quantity);
            return this;
        }

        public Builder buildNumberInPackage(int amount) {
            medicine.setNumberInPackage(amount);
            return this;
        }

        public Builder buildFormId(long id) {
            medicine.setFormId(id);
            return this;
        }

        public Builder buildDosage(int dosage) {
            medicine.setDosage(dosage);
            return this;
        }

        public Builder buildDosageUnit(DosageUnit unit) {
            medicine.setDosageUnit(unit);
            return this;
        }

        public Builder buildIngredients(String ingredients) {
            medicine.setIngredients(ingredients);
            return this;
        }

        public Builder buildNeedPrescription(boolean needPrescription) {
            medicine.setNeedPrescription(needPrescription);
            return this;
        }

        public Builder buildManufacturerId(long id) {
            medicine.setManufacturerId(id);
            return this;
        }

        public Builder buildInstruction(String instruction) {
            medicine.setInstruction(instruction);
            return this;
        }

        public Builder buildImagePath(String path) {
            medicine.setImagePath(path);
            return this;
        }

        public Medicine build() {
            return medicine;
        }
    }
}
