package com.epam.pharmacy.util;

import com.epam.pharmacy.model.entity.Medicine;

import java.util.Comparator;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.MEDICINE;

/**
 * The type compare cart positions in ascending order of medicine id.
 */
public class CartPositionsInAscendingMedicineIdComparator implements Comparator<Map<String, Object>> {
    /**
     * Compare cart positions in ascending order of medicine id.
     *
     * @param o1 the first position data map
     * @param o2 the second position data map
     * @return a negative integer, zero, or a positive integer as the first position medicine id is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        Medicine m1 = (Medicine) o1.get(MEDICINE);
        Medicine m2 = (Medicine) o2.get(MEDICINE);
        return Long.compare(m1.getId(), m2.getId());
    }
}
