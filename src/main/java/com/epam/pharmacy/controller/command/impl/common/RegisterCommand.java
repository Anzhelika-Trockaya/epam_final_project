package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.mapper.impl.RegisterUserRequestMapper;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public class RegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RegisterUserRequestMapper mapper = RegisterUserRequestMapper.getInstance();
        Map<String, String> userData = mapper.mapRequest(request);
        HttpSession session = request.getSession();
        if (UserRole.ADMIN != session.getAttribute(AttributeName.CURRENT_USER_ROLE)) {
            userData.put(ParameterName.USER_ROLE, UserRole.CUSTOMER.name());
        }
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        String page;
        Router router;
        try {
            boolean isCreated = userService.create(userData);
            router = new Router();
            page = (String) session.getAttribute(AttributeName.CURRENT_PAGE);
            if (isCreated) {
                request.setAttribute(AttributeName.SUCCESSFUL_REGISTRATION, true);
                if (session.getAttribute(AttributeName.CURRENT_USER_ROLE) == null) {
                    page = PagePath.SIGN_IN;
                }
            } else {
                addDataToRequest(request, userData);
            }
            router.setPage(page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }

    private void addDataToRequest(HttpServletRequest request, Map<String, String> userData) {
        for (String key : userData.keySet()) {
            request.setAttribute(key, userData.get(key));
        }
    }
}
