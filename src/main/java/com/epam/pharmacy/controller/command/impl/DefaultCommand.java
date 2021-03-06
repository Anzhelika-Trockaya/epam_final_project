package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The type Default command.
 */
public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = PagePath.ERROR_500_PAGE;
        return new Router(page, Router.Type.REDIRECT);
    }
}

