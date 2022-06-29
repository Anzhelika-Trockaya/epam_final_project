package com.epam.pharmacy.util;

import com.epam.pharmacy.model.entity.Prescription;

import java.util.Comparator;

public class PrescriptionByDateComparator implements Comparator<Prescription> {
    @Override
    public int compare(Prescription o1, Prescription o2) {
        return o1.getExpirationDate().compareTo(o2.getExpirationDate());
    }
}
