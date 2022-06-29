package com.epam.pharmacy.util;

import com.epam.pharmacy.model.entity.Medicine;

import java.util.Comparator;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.MEDICINE;

public class CartPositionsIdAscendingMedicineIdComparator implements Comparator<Map<String, Object>> {
    @Override
    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        Medicine m1 = (Medicine) o1.get(MEDICINE);
        Medicine m2 = (Medicine) o2.get(MEDICINE);
        return Long.compare(m1.getId(),m2.getId());
    }
}
