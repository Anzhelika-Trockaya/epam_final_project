package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;

public class SearchUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        Map<String, String> paramsMap = createSearchParamsMap(request);
        HttpSession session = request.getSession();
        Router router;
        String listAttributeName;
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        if (UserRole.ADMIN == currentUserRole) {
            listAttributeName = USERS_LIST;
            router = new Router(PagePath.USERS_PAGE);
        } else {
            listAttributeName = CUSTOMERS_LIST;
            paramsMap.put(ParameterName.USER_ROLE, "CUSTOMER");
            paramsMap.put(ParameterName.USER_STATE, "ACTIVE");
            router = new Router(PagePath.CUSTOMERS_PAGE);
        }
        try {
            List<User> users = userService.findByParams(new HashMap<>(paramsMap));
            request.setAttribute(listAttributeName, users);
            ContentFiller contentFiller = ContentFiller.getInstance();
            contentFiller.addDataToRequest(request, paramsMap);
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the SearchUserCommand", e);
            throw new CommandException("Exception in the SearchUserCommand", e);
        }
    }

    private Map<String, String> createSearchParamsMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        String lastname = request.getParameter(LASTNAME).trim();
        params.put(ParameterName.USER_LASTNAME, lastname);
        String name = request.getParameter(NAME).trim();
        params.put(ParameterName.USER_NAME, name);
        String patronymic = request.getParameter(PATRONYMIC).trim();
        params.put(ParameterName.USER_PATRONYMIC, patronymic);
        String birthdayDate = request.getParameter(BIRTHDAY_DATE);
        params.put(ParameterName.USER_BIRTHDAY_DATE, birthdayDate);
        String role = request.getParameter(ROLE);
        params.put(ParameterName.USER_ROLE, role);
        String state = request.getParameter(ParameterName.STATE);
        params.put(ParameterName.USER_STATE, state);
        return params;
    }
}
