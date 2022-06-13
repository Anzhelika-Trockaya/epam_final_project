package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.command.impl.*;
import com.epam.pharmacy.controller.command.impl.admin.ChangeUserStateCommand;
import com.epam.pharmacy.controller.command.impl.admin.GoChangeUsersPageCommand;
import com.epam.pharmacy.controller.command.impl.admin.DeleteUserCommand;
import com.epam.pharmacy.controller.command.impl.common.ChangeLanguageCommand;
import com.epam.pharmacy.controller.command.impl.common.RegisterCommand;
import com.epam.pharmacy.controller.command.impl.common.SignInCommand;
import com.epam.pharmacy.controller.command.impl.customer.AddMedicineToCartCommand;
import com.epam.pharmacy.controller.command.impl.customer.SearchMedicineCommand;
import com.epam.pharmacy.controller.command.impl.pharmacist.*;

public enum CommandType {
    ADD_MEDICINE(new AddMedicineCommand()),
    ADD_INTERNATIONAL_NAME(new AddInternationalNameCommand()),
    ADD_MEDICINE_FORM(new AddMedicineFormCommand()),
    ADD_MANUFACTURER(new AddManufacturerCommand()),
    ADD_MEDICINE_TO_CART(new AddMedicineToCartCommand()),
    GO_CHANGE_USERS_PAGE(new GoChangeUsersPageCommand()),
    GO_ADD_MEDICINE_PAGE(new GoAddMedicinePageCommand()),
    GO_HOME_PAGE(new GoHomePageCommand()),
    GO_MEDICINES_PAGE(new GoMedicinesPageCommand()),
    GO_INTERNATIONAL_NAMES_PAGE(new GoInternationalNamesPageCommand()),
    GO_FORMS_PAGE(new GoFormsPageCommand()),
    GO_MANUFACTURERS_PAGE(new GoManufacturersPageCommand()),
    GO_EDIT_MEDICINE_PAGE(new GoEditMedicinePageCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    CHANGE_USER_STATE(new ChangeUserStateCommand()),
    EDIT_INTERNATIONAL_NAME(new EditInternationalNameCommand()),
    EDIT_MEDICINE_FORM(new EditMedicineFormCommand()),
    EDIT_MANUFACTURER(new EditManufacturerCommand()),
    DELETE_USER(new DeleteUserCommand()),
    DELETE_INTERNATIONAL_NAME(new DeleteInternationalNameCommand()),
    DELETE_MEDICINE_FORM(new DeleteMedicineFormCommand()),
    DELETE_MANUFACTURER(new DeleteManufacturerCommand()),
    DEFAULT(new DefaultCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),
    SEARCH_MEDICINE(new SearchMedicineCommand()),
    SIGN_IN(new SignInCommand());

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

