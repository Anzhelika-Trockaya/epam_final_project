package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class UpdatePasswordCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return null;//FIXME
    }
}
