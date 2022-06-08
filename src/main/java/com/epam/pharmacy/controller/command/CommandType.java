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
    ADD_MEDICINE_TO_CART(new AddMedicineToCartCommand()),
    GO_CHANGE_USERS_PAGE(new GoChangeUsersPageCommand()),
    GO_ADD_MEDICINE_PAGE(new GoAddMedicinePageCommand()),
    GO_HOME_PAGE(new GoHomePageCommand()),
    GO_CHANGE_MEDICINES_PAGE(new GoChangeMedicinesPageCommand()),
    GO_CHANGE_INTERNATIONAL_NAMES_PAGE(new GoChangeInternationalNamesPageCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    CHANGE_USER_STATE(new ChangeUserStateCommand()),
    EDIT_INTERNATIONAL_NAME(new EditInternationalNameCommand()),
    DELETE_USER(new DeleteUserCommand()),
    DELETE_INTERNATIONAL_NAME(new DeleteInternationalNameCommand()),
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

