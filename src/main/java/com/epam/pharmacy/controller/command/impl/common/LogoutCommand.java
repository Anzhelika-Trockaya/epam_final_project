package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> attrNames = session.getAttributeNames();
        while(attrNames.hasMoreElements()){
            session.removeAttribute(attrNames.nextElement());
        }
        return new Router(PagePath.HOME, Router.Type.REDIRECT);
    }
}
