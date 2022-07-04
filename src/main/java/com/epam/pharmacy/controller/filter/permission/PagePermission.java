package com.epam.pharmacy.controller.filter.permission;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static com.epam.pharmacy.controller.PagePath.*;

/**
 * The enum Page permission.
 */
public enum PagePermission {//todo delete
    /**
     * Admin page permission.
     */
    ADMIN(new HashSet<>(Arrays.asList(
            HOME_PAGE,
            MEDICINES_PAGE,
            ORDER_PAGE,
            ORDERS_PAGE,
            REGISTRATION_PAGE,
            USER_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE,
            ADD_MEDICINE_PAGE,
            FORMS_PAGE,
            INTERNATIONAL_NAMES_PAGE,
            MANUFACTURERS_PAGE,
            USERS_PAGE))),
    /**
     * Pharmacist page permission.
     */
    PHARMACIST(new HashSet<>(Arrays.asList(
            HOME_PAGE,
            MEDICINES_PAGE,
            ORDER_PAGE,
            ORDERS_PAGE,
            USER_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE,
            ADD_MEDICINE_PAGE,
            FORMS_PAGE,
            INTERNATIONAL_NAMES_PAGE,
            MANUFACTURERS_PAGE))),
    /**
     * Customer page permission.
     */
    CUSTOMER(new HashSet<>(Arrays.asList(
            HOME_PAGE,
            MEDICINES_PAGE,
            ORDER_PAGE,
            ORDERS_PAGE,
            USER_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE,
            BALANCE_PAGE,
            CART_PAGE,
            PRESCRIPTIONS_PAGE))),
    /**
     * Doctor page permission.
     */
    DOCTOR(new HashSet<>(Arrays.asList(
            HOME_PAGE,
            MEDICINES_PAGE,
            ORDER_PAGE,
            ORDERS_PAGE,
            USER_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE,
            PRESCRIPTIONS_PAGE,
            ADD_PRESCRIPTION_PAGE,
            CUSTOMERS_PAGE))),
    /**
     * Guest page permission.
     */
    GUEST(new HashSet<>(Arrays.asList(
            HOME_PAGE,
            SIGN_IN_PAGE,
            REGISTRATION_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE)));

    private final Set<String> userPages;

    PagePermission(Set<String> userPages){
        this.userPages = userPages;
    }

    /**
     * Get user pages set.
     *
     * @return the set
     */
    public Set<String> getUserPages(){
        return userPages;
    }
}
