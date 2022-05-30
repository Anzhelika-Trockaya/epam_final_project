package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

public class GoHomePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {//fixme
        return null;
    }
}
