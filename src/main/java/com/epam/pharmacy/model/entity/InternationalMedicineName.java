package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class InternationalMedicineName extends CustomEntity implements Serializable {
    private static final long serialVersionUID = 7482338068541838620L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String internationalName;

    public InternationalMedicineName() {
        id = DEFAULT_ID;
    }

    public InternationalMedicineName(String internationalName) {
        id = DEFAULT_ID;
        this.internationalName = internationalName;
    }

    public InternationalMedicineName(long id, String internationalName) {
        this.id = id;
        this.internationalName = internationalName;
    }

    public long getId() {
        return id;
    }

    public String getInternationalName() {
        return internationalName;
    }

    public void setInternationalName(String internationalName) {
        this.internationalName = internationalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternationalMedicineName that = (InternationalMedicineName) o;
        return id == that.id && Objects.equals(internationalName, that.internationalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, internationalName);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", internationalName='" + internationalName + '\'' +
                '}';
    }
}
