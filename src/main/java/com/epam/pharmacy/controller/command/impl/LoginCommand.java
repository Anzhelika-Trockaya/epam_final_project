package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.Parameter;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(Parameter.LOGIN);
        String password = request.getParameter(Parameter.PASSWORD);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        String page;
        HttpSession session = request.getSession();
        try {
            Optional<UserRole> userRoleOptional = userService.authenticate(login, password);
            if (userRoleOptional.isPresent()) {
                session.setAttribute(SessionAttribute.USER_NAME, login);
                session.setAttribute(SessionAttribute.USER_ROLE, userRoleOptional.get());
                page = PagePath.HOME;
            } else {
                request.setAttribute(SessionAttribute.FAILED_LOGIN_MESSAGE, "incorrect login or pass");//fixme magic value
                page = PagePath.SIGN_IN;
            }
            session.setAttribute(SessionAttribute.CURRENT_PAGE, page);//fixme
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(page);
    }
}

