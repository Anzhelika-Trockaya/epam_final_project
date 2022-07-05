package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Medicine form.
 */
public class MedicineForm extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 2532449760325244454L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String name;
    private FormUnit unit;

    /**
     * Instantiates a new Medicine form with default id value.
     */
    public MedicineForm() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new Medicine form with default id value.
     *
     * @param name form name
     * @param unit form unit
     */
    public MedicineForm(String name, FormUnit unit) {
        id = DEFAULT_ID;
        this.name = name;
        this.unit = unit;
    }

    /**
     * Instantiates a new Medicine form.
     *
     * @param id   form id
     * @param name form name
     * @param unit form unit
     */
    public MedicineForm(long id, String name, FormUnit unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    /**
     * Gets medicine form id.
     *
     * @return the medicine form id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets medicine form name.
     *
     * @return the medicine form name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets medicine form name.
     *
     * @param name the medicine form name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets medicine form unit.
     *
     * @return the medicine form unit
     */
    public FormUnit getUnit() {
        return unit;
    }

    /**
     * Sets medicine form unit.
     *
     * @param unit the medicine form id
     */
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
        return new StringBuilder(this.getClass().getSimpleName()).append("{").
                append("id=").append(id).
                append(", name='").append(name).append('\'').
                append(", unit=").append(unit).append('}').
                toString();
    }
}
