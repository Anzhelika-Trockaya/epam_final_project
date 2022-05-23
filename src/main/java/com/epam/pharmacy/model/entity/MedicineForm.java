package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class MedicineForm extends CustomEntity implements Serializable {
    //fixme serialVersion equals hashcode toString
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String name;
    private FormUnit unit;
    public MedicineForm(){
        id=DEFAULT_ID;
    }
    public MedicineForm(String name, FormUnit unit){
        id=DEFAULT_ID;
        this.name=name;
        this.unit=unit;
    }
    public MedicineForm(long id, String name, FormUnit unit){
        this.id=id;
        this.name=name;
        this.unit=unit;
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

    public FormUnit getUnit() {
        return unit;
    }

    public void setUnit(FormUnit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineForm that = (MedicineForm) o;
        return id == that.id && Objects.equals(name, that.name) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unit);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit=" + unit +
                '}';
    }
}
