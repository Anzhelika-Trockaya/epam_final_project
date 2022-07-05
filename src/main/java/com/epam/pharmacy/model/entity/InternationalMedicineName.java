package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type International medicine name.
 */
public class InternationalMedicineName extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 7482338068541838620L;
    private static final long DEFAULT_ID = 0L;
    private final long id;
    private String internationalName;

    /**
     * Instantiates a new International medicine name with default id value.
     */
    public InternationalMedicineName() {
        id = DEFAULT_ID;
    }

    /**
     * Instantiates a new International medicine name.
     *
     * @param internationalName international name
     */
    public InternationalMedicineName(String internationalName) {
        id = DEFAULT_ID;
        this.internationalName = internationalName;
    }

    /**
     * Instantiates a new International medicine name.
     *
     * @param id                international name id
     * @param internationalName international name
     */
    public InternationalMedicineName(long id, String internationalName) {
        this.id = id;
        this.internationalName = internationalName;
    }

    /**
     * Gets international medicine name id.
     *
     * @return the international medicine name id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets international medicine name.
     *
     * @return the international medicine name
     */
    public String getInternationalName() {
        return internationalName;
    }

    /**
     * Sets international medicine name.
     *
     * @param internationalName the international medicine name
     */
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
