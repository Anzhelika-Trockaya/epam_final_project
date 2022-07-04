package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.AttributeName.LOGIN;
import static com.epam.pharmacy.controller.AttributeName.PASSWORD;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.USER_ADDRESS;
import static com.epam.pharmacy.controller.ParameterName.USER_PHONE;
import static com.epam.pharmacy.controller.ParameterName.USER_SEX;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> userData = createUserDataMap(request);
        HttpSession session = request.getSession();
        if (UserRole.ADMIN != session.getAttribute(CURRENT_USER_ROLE)) {
            userData.put(ParameterName.USER_ROLE, UserRole.CUSTOMER.name());
        }
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        Router router;
        String page = (String) session.getAttribute(AttributeName.CURRENT_PAGE);
        try {
            boolean isCreated = userService.create(userData);
            if (isCreated) {
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_REGISTRATION, true);
                if (session.getAttribute(CURRENT_USER_ROLE) == null) {
                    page = PagePath.SIGN_IN_PAGE;
                }
                router = new Router(page, Router.Type.REDIRECT);
            } else {
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addDataToRequest(request, userData);
                router = new Router(page);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the RegisterCommand", e);
            throw new CommandException("Exception in the RegisterCommand", e);
        }
        return router;
    }

    public Map<String, String> createUserDataMap(HttpServletRequest request) {
        Map<String, String> userData = new HashMap<>();
        String login = request.getParameter(LOGIN);
        userData.put(USER_LOGIN, login);
        String lastname = request.getParameter(LASTNAME);
        userData.put(ParameterName.USER_LASTNAME, lastname);
        String name = request.getParameter(NAME);
        userData.put(ParameterName.USER_NAME, name);
        String patronymic = request.getParameter(PATRONYMIC);
        userData.put(ParameterName.USER_PATRONYMIC, patronymic);
        String password = request.getParameter(PASSWORD);
        userData.put(USER_PASSWORD, password);
        String birthdayDate = request.getParameter(BIRTHDAY_DATE);
        userData.put(ParameterName.USER_BIRTHDAY_DATE, birthdayDate);
        String sex = request.getParameter(SEX);
        userData.put(USER_SEX, sex);
        String userRole = request.getParameter(ROLE);
        userData.put(ParameterName.USER_ROLE, userRole);
        String phone = request.getParameter(PHONE);
        userData.put(USER_PHONE, phone);
        String address = request.getParameter(ADDRESS);
        userData.put(USER_ADDRESS, address);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);
        userData.put(REPEAT_PASSWORD, repeatPassword);
        return userData;
    }
}
