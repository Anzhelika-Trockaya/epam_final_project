package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Manufacturer extends CustomEntity implements Serializable {
    private static final long serialVersionUID = 6580462204398816342L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String name;
    private String country;

    public Manufacturer() {
        id = DEFAULT_ID;
    }

    public Manufacturer(String name, String country) {
        id = DEFAULT_ID;
        this.name = name;
        this.country = country;
    }

    public Manufacturer(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getSimpleName()).append("{").
                append("id=").append(id).
                append(", name='").append(name).append('\'').
                append(", country='").append(country).append('\'').
                append('}').toString();
    }
}
