package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Manufacturer.
 */
public class Manufacturer extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 6580462204398816342L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String name;
    private String country;

    /**
     * Instantiates a new Manufacturer with default id value.
     */
    public Manufacturer() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new Manufacturer with default id value.
     *
     * @param name    manufacturer name
     * @param country country
     */
    public Manufacturer(String name, String country) {
        id = DEFAULT_ID;
        this.name = name;
        this.country = country;
    }

    /**
     * Instantiates a new Manufacturer.
     *
     * @param id      manufacturer id
     * @param name    manufacturer name
     * @param country country
     */
    public Manufacturer(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /**
     * Gets manufacturer id.
     *
     * @return the manufacturer id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets manufacturer name.
     *
     * @return the manufacturer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets manufacturer name.
     *
     * @param name the manufacturer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets manufacturer country.
     *
     * @return the manufacturer country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets manufacturer country.
     *
     * @param country the manufacturer country
     */
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
