package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = (String) request.getSession().getAttribute(AttributeName.CURRENT_PAGE);//todo current page??? request params? maybe home page
        return new Router(page, Router.Type.REDIRECT);
    }
}

