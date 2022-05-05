package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.mapper.impl.AddUserRequestMapper;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;

public class RegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        AddUserRequestMapper mapper = AddUserRequestMapper.getInstance();
        Map<String, String> userData = mapper.mapRequest(request);
        HttpSession session = request.getSession();
        if (UserRole.ADMIN != session.getAttribute(USER_ROLE)) {
            userData.put(ParameterName.USER_ROLE, UserRole.CUSTOMER.name());
        }
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        String page;
        Router router;
        try {
            boolean isCreated = userService.create(userData);
            if (isCreated) {
                request.getSession().setAttribute(SUCCESSFUL_REGISTRATION, true);//fixme session attr
                page = PagePath.SIGN_IN;
                router = new Router(page, Router.Type.REDIRECT);
            } else {
                request.setAttribute(USER_DATA, userData);
                page = PagePath.REGISTRATION;
                router = new Router(page);
            }
            if (UserRole.ADMIN == session.getAttribute(USER_ROLE)) {
                page = (String) session.getAttribute(CURRENT_PAGE);
                router = new Router(page);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
