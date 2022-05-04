package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router(PagePath.HOME);
    }
}
