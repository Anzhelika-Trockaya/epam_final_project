package com.epam.pharmacy.model.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.epam.pharmacy.controller.PagePath.*;
import static com.epam.pharmacy.controller.command.CommandType.*;


/**
 * The enum User role. Defines the available pages and commands for roles.
 */
public enum UserRole implements Serializable {
    /**
     * Admin role.
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
            USERS_PAGE)),
            new HashSet<>(Arrays.asList(
                    ADD_MEDICINE.name(),
                    ADD_INTERNATIONAL_NAME.name(),
                    ADD_MEDICINE_FORM.name(),
                    ADD_MANUFACTURER.name(),
                    GET_ORDER_FOR_PROCESS.name(),
                    GO_EDIT_MEDICINE_PAGE.name(),
                    GO_ORDERS_PAGE.name(),
                    GO_ORDER_PAGE.name(),
                    CHANGE_LANGUAGE.name(),
                    CHANGE_PASSWORD.name(),
                    CHANGE_USER_STATE.name(),
                    COMPLETE_ORDER.name(),
                    EDIT_INTERNATIONAL_NAME.name(),
                    EDIT_MEDICINE.name(),
                    EDIT_MEDICINE_FORM.name(),
                    EDIT_MANUFACTURER.name(),
                    EDIT_USER_DATA.name(),
                    DELETE_INTERNATIONAL_NAME.name(),
                    DELETE_MEDICINE_FORM.name(),
                    DELETE_MANUFACTURER.name(),
                    DEFAULT.name(),
                    LOGOUT.name(),
                    REGISTER.name(),
                    SEARCH_MEDICINES.name(),
                    SEARCH_USER.name()
            ))),
    /**
     * Pharmacist role.
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
            MANUFACTURERS_PAGE)),
            new HashSet<>(Arrays.asList(
                    ADD_MEDICINE.name(),
                    ADD_INTERNATIONAL_NAME.name(),
                    ADD_MEDICINE_FORM.name(),
                    ADD_MANUFACTURER.name(),
                    GET_ORDER_FOR_PROCESS.name(),
                    GO_EDIT_MEDICINE_PAGE.name(),
                    GO_ORDERS_PAGE.name(),
                    GO_ORDER_PAGE.name(),
                    CHANGE_LANGUAGE.name(),
                    CHANGE_PASSWORD.name(),
                    COMPLETE_ORDER.name(),
                    EDIT_INTERNATIONAL_NAME.name(),
                    EDIT_MEDICINE.name(),
                    EDIT_MEDICINE_FORM.name(),
                    EDIT_MANUFACTURER.name(),
                    EDIT_USER_DATA.name(),
                    DELETE_INTERNATIONAL_NAME.name(),
                    DELETE_MEDICINE_FORM.name(),
                    DELETE_MANUFACTURER.name(),
                    DEFAULT.name(),
                    LOGOUT.name(),
                    SEARCH_MEDICINES.name()
            ))),
    /**
     * Customer role.
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
            PRESCRIPTIONS_PAGE)),
            new HashSet<>(Arrays.asList(
                    ADD_MEDICINE_TO_CART.name(),
                    GO_PRESCRIPTIONS_PAGE.name(),
                    GO_ORDERS_PAGE.name(),
                    GO_ORDER_PAGE.name(),
                    GO_CART.name(),
                    CHANGE_LANGUAGE.name(),
                    CHANGE_PASSWORD.name(),
                    CHANGE_MEDICINE_QUANTITY_IN_CART.name(),
                    CLEAR_CART.name(),
                    EDIT_USER_DATA.name(),
                    DELETE_POSITION_FROM_CART.name(),
                    DEFAULT.name(),
                    DEPOSIT_TO_USER_ACCOUNT.name(),
                    ORDER.name(),
                    REQUEST_RENEWAL_PRESCRIPTION.name(),
                    LOGOUT.name(),
                    SEARCH_MEDICINES.name(),
                    SHOW_MEDICINES_FOR_PRESCRIPTION.name()
            ))),
    /**
     * Doctor role.
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
            CUSTOMERS_PAGE)),
            new HashSet<>(Arrays.asList(
                    ADD_PRESCRIPTION.name(),
                    GO_CUSTOMERS_PAGE.name(),
                    GO_ADD_PRESCRIPTION_PAGE.name(),
                    GO_PRESCRIPTIONS_PAGE.name(),
                    CHANGE_LANGUAGE.name(),
                    CHANGE_PASSWORD.name(),
                    EDIT_USER_DATA.name(),
                    DELETE_PRESCRIPTION.name(),
                    DEFAULT.name(),
                    RENEWAL_PRESCRIPTION.name(),
                    LOGOUT.name(),
                    SEARCH_MEDICINES.name(),
                    SEARCH_USER.name()
            ))),
    /**
     * Guest role.
     */
    GUEST(new HashSet<>(Arrays.asList(
            HOME_PAGE,
            SIGN_IN_PAGE,
            REGISTRATION_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE)),
            new HashSet<>(Arrays.asList(
                    CHANGE_LANGUAGE.name(),
                    DEFAULT.name(),
                    REGISTER.name(),
                    SIGN_IN.name()
            )));
    private final Set<String> availablePages;
    private final Set<String> availableCommands;

    UserRole(Set<String> availablePages, Set<String> availableCommands) {
        this.availablePages = availablePages;
        this.availableCommands = availableCommands;
    }

    /**
     * Get available pages set.
     *
     * @return the set
     */
    public Set<String> getAvailablePages() {
        return availablePages;
    }

    /**
     * Get available commands set.
     *
     * @return the set
     */
    public Set<String> getAvailableCommands() {
        return availableCommands;
    }
}
