package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Manufacturer extends CustomEntity implements Serializable {
    private static final long serialVersionUID = 6580462204398816342L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String name;
    private String country;
    private String address;

    public Manufacturer() {
        id = DEFAULT_ID;
    }

    public Manufacturer(String name, String country, String address) {
        id = DEFAULT_ID;
        this.name = name;
        this.country = country;
        this.address = address;
    }

    public Manufacturer(long id, String name, String country, String address) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(country, that.country) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, address);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
