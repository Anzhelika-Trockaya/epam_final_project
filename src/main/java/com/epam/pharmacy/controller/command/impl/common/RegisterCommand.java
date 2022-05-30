package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.LOGIN;
import static com.epam.pharmacy.controller.ParameterName.PASSWORD;

public class RegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> userData = createUserDataMap(request);
        HttpSession session = request.getSession();
        if (UserRole.ADMIN != session.getAttribute(AttributeName.CURRENT_USER_ROLE)) {
            userData.put(USER_ROLE, UserRole.CUSTOMER.name());
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
                request.setAttribute(AttributeName.SUCCESSFUL_REGISTRATION, true);//fixme to session
                if (session.getAttribute(AttributeName.CURRENT_USER_ROLE) == null) {
                    page = PagePath.SIGN_IN;
                }
               //fixme router.setTypeRedirect();
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

    private Map<String, String> createUserDataMap(HttpServletRequest request) {
        Map<String, String> userData = new HashMap<>();
        String login = request.getParameter(LOGIN);
        userData.put(USER_LOGIN, login);
        String lastname = request.getParameter(LASTNAME);
        userData.put(USER_LASTNAME, lastname);
        String name = request.getParameter(NAME);
        userData.put(USER_NAME, name);
        String patronymic = request.getParameter(PATRONYMIC);
        userData.put(USER_PATRONYMIC, patronymic);
        String password = request.getParameter(PASSWORD);
        userData.put(USER_PASSWORD, password);
        String birthdayDate = request.getParameter(BIRTHDAY_DATE);
        userData.put(USER_BIRTHDAY_DATE, birthdayDate);
        String sex = request.getParameter(SEX);
        userData.put(USER_SEX, sex);
        String userRole = request.getParameter(ROLE);
        userData.put(USER_ROLE, userRole);
        String phone = request.getParameter(PHONE);
        userData.put(USER_PHONE, phone);
        String address = request.getParameter(ADDRESS);
        userData.put(USER_ADDRESS, address);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);
        userData.put(REPEAT_PASSWORD, repeatPassword);
        return userData;
    }
}
