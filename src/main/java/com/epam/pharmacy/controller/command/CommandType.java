package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.command.impl.*;
import com.epam.pharmacy.controller.command.impl.admin.ChangeUserStateCommand;
import com.epam.pharmacy.controller.command.impl.admin.SearchUserCommand;
import com.epam.pharmacy.controller.command.impl.common.*;
import com.epam.pharmacy.controller.command.impl.customer.*;
import com.epam.pharmacy.controller.command.impl.doctor.*;
import com.epam.pharmacy.controller.command.impl.pharmacist.*;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Add medicine.
     */
    ADD_MEDICINE(new AddMedicineCommand()),
    /**
     * The Add international name.
     */
    ADD_INTERNATIONAL_NAME(new AddInternationalNameCommand()),
    /**
     * The Add medicine form.
     */
    ADD_MEDICINE_FORM(new AddMedicineFormCommand()),
    /**
     * The Add manufacturer.
     */
    ADD_MANUFACTURER(new AddManufacturerCommand()),
    /**
     * The Add medicine to cart.
     */
    ADD_MEDICINE_TO_CART(new AddMedicineToCartCommand()),
    /**
     * The Add prescription.
     */
    ADD_PRESCRIPTION(new AddPrescriptionCommand()),
    /**
     * The Get order for process.
     */
    GET_ORDER_FOR_PROCESS(new GetOrderForProcessCommand()),
    /**
     * The Go customers page.
     */
    GO_CUSTOMERS_PAGE(new GoCustomersPageCommand()),
    /**
     * The Go add prescription page.
     */
    GO_ADD_PRESCRIPTION_PAGE(new GoAddPrescriptionPageCommand()),
    /**
     * The Go edit medicine page.
     */
    GO_EDIT_MEDICINE_PAGE(new GoEditMedicinePageCommand()),
    /**
     * The Go prescriptions page.
     */
    GO_PRESCRIPTIONS_PAGE(new GoPrescriptionsPageCommand()),
    /**
     * The Go orders page.
     */
    GO_ORDERS_PAGE(new GoOrdersPageCommand()),
    /**
     * The Go order page.
     */
    GO_ORDER_PAGE(new GoOrderPageCommand()),
    /**
     * The Go cart.
     */
    GO_CART(new GoCartCommand()),
    /**
     * The Change language.
     */
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    /**
     * The Change password.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    /**
     * The Change user state.
     */
    CHANGE_USER_STATE(new ChangeUserStateCommand()),
    /**
     * The Change medicine quantity in cart.
     */
    CHANGE_MEDICINE_QUANTITY_IN_CART(new ChangeMedicineQuantityInCartCommand()),
    /**
     * The Clear cart.
     */
    CLEAR_CART(new ClearCartCommand()),
    /**
     * The Complete order.
     */
    COMPLETE_ORDER(new CompleteOrderCommand()),
    /**
     * The Edit international name.
     */
    EDIT_INTERNATIONAL_NAME(new EditInternationalNameCommand()),
    /**
     * The Edit medicine.
     */
    EDIT_MEDICINE(new EditMedicineCommand()),
    /**
     * The Edit medicine form.
     */
    EDIT_MEDICINE_FORM(new EditMedicineFormCommand()),
    /**
     * The Edit manufacturer.
     */
    EDIT_MANUFACTURER(new EditManufacturerCommand()),
    /**
     * The Edit user data.
     */
    EDIT_USER_DATA(new EditUserDataCommand()),
    /**
     * The Delete international name.
     */
    DELETE_INTERNATIONAL_NAME(new DeleteInternationalNameCommand()),
    /**
     * The Delete medicine form.
     */
    DELETE_MEDICINE_FORM(new DeleteMedicineFormCommand()),
    /**
     * The Delete manufacturer.
     */
    DELETE_MANUFACTURER(new DeleteManufacturerCommand()),
    /**
     * The Delete prescription.
     */
    DELETE_PRESCRIPTION(new DeletePrescriptionCommand()),
    /**
     * The Delete position from cart.
     */
    DELETE_POSITION_FROM_CART(new DeletePositionFromCartCommand()),
    /**
     * The Default.
     */
    DEFAULT(new DefaultCommand()),
    /**
     * The Deposit to user account.
     */
    DEPOSIT_TO_USER_ACCOUNT(new DepositToUserAccountCommand()),
    /**
     * The Order.
     */
    ORDER(new OrderCommand()),
    /**
     * The Renewal prescription.
     */
    RENEWAL_PRESCRIPTION(new RenewalPrescriptionCommand()),
    /**
     * The Request renewal prescription.
     */
    REQUEST_RENEWAL_PRESCRIPTION(new RequestRenewalPrescriptionCommand()),
    /**
     * The Logout.
     */
    LOGOUT(new LogoutCommand()),
    /**
     * The Register.
     */
    REGISTER(new RegisterCommand()),
    /**
     * The Search medicines.
     */
    SEARCH_MEDICINES(new SearchMedicinesCommand()),
    /**
     * The Search user.
     */
    SEARCH_USER(new SearchUserCommand()),
    /**
     * The Show medicines for prescription.
     */
    SHOW_MEDICINES_FOR_PRESCRIPTION(new ShowMedicinesForPrescriptionCommand()),
    /**
     * The Sign in.
     */
    SIGN_IN(new SignInCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Finds command by name. If command not found return the default command.
     *
     * @param commandStr the command str
     * @return the command
     */
    public static Command commandOf(String commandStr) {
        String commandName = commandStr.toUpperCase();
        CommandType[] values = CommandType.values();
        for (CommandType value : values) {
            if (value.name().equals(commandName)) {
                CommandType commandType = CommandType.valueOf(commandName);
                return commandType.getCommand();
            }
        }
        return DEFAULT.getCommand();
    }
}

