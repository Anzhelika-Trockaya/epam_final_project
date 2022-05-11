package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.command.impl.*;
import com.epam.pharmacy.controller.command.impl.admin.ChangeUsersCommand;
import com.epam.pharmacy.controller.command.impl.common.ChangeLanguageCommand;
import com.epam.pharmacy.controller.command.impl.common.RegisterCommand;
import com.epam.pharmacy.controller.command.impl.common.SignInCommand;

public enum CommandType {
    GO_CHANGE_USERS_PAGE(new ChangeUsersCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
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

    public static Command define(String commandStr) {
        CommandType current;
        String commandName = commandStr.toUpperCase();
        if (existCommand(commandName)) {
            current = CommandType.valueOf(commandName);
        } else {
            current = DEFAULT;
        }
        return current.getCommand();
    }

    private static boolean existCommand(String commandName) {
        CommandType[] values = CommandType.values();
        for (CommandType value : values) {
            if (value.name().equals(commandName)) {
                return true;
            }
        }
        return false;
    }
}

