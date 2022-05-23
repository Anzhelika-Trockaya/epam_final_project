package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
