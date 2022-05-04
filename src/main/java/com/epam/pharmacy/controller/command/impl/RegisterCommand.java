package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Parameter;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.SessionAttribute;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.mapper.impl.AddUserRequestMapper;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public class RegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        AddUserRequestMapper mapper = AddUserRequestMapper.getInstance();
        Map<String, String> userData = mapper.mapRequest(request);
        HttpSession session = request.getSession();
        if (UserRole.ADMIN != session.getAttribute(SessionAttribute.USER_ROLE)) {
            userData.put(Parameter.USER_ROLE, UserRole.CUSTOMER.name());
        }
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        String page;
        try {
            boolean isCreated = userService.create(userData);
            if (isCreated) {
                session.setAttribute(SessionAttribute.SUCCESSFUL_REGISTRATION_MESSAGE, "Registration was successful.");
                page = PagePath.SIGN_IN;//fixme redirect
            } else {
                String message = userData.get(Parameter.INCORRECT_DATA_INFO);
                request.setAttribute(SessionAttribute.FAILED_REGISTRATION_MESSAGE, "Incorrect data: " + message);//fixme magic value
                page = PagePath.REGISTRATION;
            }
            if (UserRole.ADMIN == session.getAttribute(SessionAttribute.USER_ROLE)) {
                page=(String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(page);
    }
}
