package com.epam.pharmacy.model.entity;

import java.io.Serializable;

public enum UserRole implements Serializable {
    //fixme serialVersion
    ADMIN,
    PHARMACIST,
    DOCTOR,
    CUSTOMER
}
