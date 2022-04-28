package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.command.impl.AddUserCommand;
import com.epam.pharmacy.controller.command.impl.DefaultCommand;
import com.epam.pharmacy.controller.command.impl.LoginCommand;
import com.epam.pharmacy.controller.command.impl.LogoutCommand;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand());
    Command command;

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

