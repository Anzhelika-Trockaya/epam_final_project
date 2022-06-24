package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.command.impl.*;
import com.epam.pharmacy.controller.command.impl.admin.ChangeUserStateCommand;
import com.epam.pharmacy.controller.command.impl.admin.DeleteUserCommand;
import com.epam.pharmacy.controller.command.impl.admin.SearchUserCommand;
import com.epam.pharmacy.controller.command.impl.common.*;
import com.epam.pharmacy.controller.command.impl.customer.AddMedicineToCartCommand;
import com.epam.pharmacy.controller.command.impl.customer.RequestRenewalPrescriptionCommand;
import com.epam.pharmacy.controller.command.impl.doctor.*;
import com.epam.pharmacy.controller.command.impl.pharmacist.*;

public enum CommandType {
    ADD_MEDICINE(new AddMedicineCommand()),
    ADD_INTERNATIONAL_NAME(new AddInternationalNameCommand()),
    ADD_MEDICINE_FORM(new AddMedicineFormCommand()),
    ADD_MANUFACTURER(new AddManufacturerCommand()),
    ADD_MEDICINE_TO_CART(new AddMedicineToCartCommand()),
    ADD_PRESCRIPTION(new AddPrescriptionCommand()),
    GO_CUSTOMERS_PAGE(new GoCustomersPageCommand()),
    GO_ADD_PRESCRIPTION_PAGE(new GoAddPrescriptionPageCommand()),
    GO_HOME_PAGE(new GoHomePageCommand()),
    GO_EDIT_MEDICINE_PAGE(new GoEditMedicinePageCommand()),
    GO_PRESCRIPTIONS_PAGE(new GoPrescriptionsPageCommand()),
    GO_USER_PAGE(new GoUserPageCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    CHANGE_USER_STATE(new ChangeUserStateCommand()),
    EDIT_INTERNATIONAL_NAME(new EditInternationalNameCommand()),
    EDIT_MEDICINE(new EditMedicineCommand()),
    EDIT_MEDICINE_FORM(new EditMedicineFormCommand()),
    EDIT_MANUFACTURER(new EditManufacturerCommand()),
    DELETE_USER(new DeleteUserCommand()),
    DELETE_INTERNATIONAL_NAME(new DeleteInternationalNameCommand()),
    DELETE_MEDICINE_FORM(new DeleteMedicineFormCommand()),
    DELETE_MANUFACTURER(new DeleteManufacturerCommand()),
    DEFAULT(new DefaultCommand()),
    RENEWAL_PRESCRIPTION(new RenewalPrescriptionCommand()),
    REQUEST_RENEWAL_PRESCRIPTION(new RequestRenewalPrescriptionCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),
    SEARCH_MEDICINES(new SearchMedicinesCommand()),
    SEARCH_USER(new SearchUserCommand()),
    SIGN_IN(new SignInCommand()),
    UPDATE_PASSWORD(new UpdatePasswordCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

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

