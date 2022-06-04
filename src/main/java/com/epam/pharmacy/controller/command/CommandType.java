package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.command.impl.*;
import com.epam.pharmacy.controller.command.impl.admin.ChangeUserStateCommand;
import com.epam.pharmacy.controller.command.impl.admin.GoChangeUsersPageCommand;
import com.epam.pharmacy.controller.command.impl.admin.DeleteUserCommand;
import com.epam.pharmacy.controller.command.impl.common.ChangeLanguageCommand;
import com.epam.pharmacy.controller.command.impl.common.RegisterCommand;
import com.epam.pharmacy.controller.command.impl.common.SignInCommand;
import com.epam.pharmacy.controller.command.impl.customer.AddMedicineToCartCommand;
import com.epam.pharmacy.controller.command.impl.pharmacist.AddMedicineCommand;
import com.epam.pharmacy.controller.command.impl.pharmacist.GoAddMedicinePageCommand;
import com.epam.pharmacy.controller.command.impl.pharmacist.GoChangeMedicinesPageCommand;

public enum CommandType {
    ADD_MEDICINE(new AddMedicineCommand()),
    ADD_MEDICINE_TO_CART(new AddMedicineToCartCommand()),
    GO_CHANGE_USERS_PAGE(new GoChangeUsersPageCommand()),
    GO_ADD_MEDICINE_PAGE(new GoAddMedicinePageCommand()),
    GO_HOME_PAGE(new GoHomePageCommand()),
    GO_CHANGE_MEDICINES_PAGE(new GoChangeMedicinesPageCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    CHANGE_USER_STATE(new ChangeUserStateCommand()),
    DELETE_USER(new DeleteUserCommand()),
    DEFAULT(new DefaultCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),
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

