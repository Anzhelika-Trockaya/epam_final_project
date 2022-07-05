package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.REPEAT_PASSWORD;
import static com.epam.pharmacy.controller.ParameterName.USER_ADDRESS;
import static com.epam.pharmacy.controller.ParameterName.USER_BIRTHDAY_DATE;
import static com.epam.pharmacy.controller.ParameterName.USER_LASTNAME;
import static com.epam.pharmacy.controller.ParameterName.USER_LOGIN;
import static com.epam.pharmacy.controller.ParameterName.USER_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_PASSWORD;
import static com.epam.pharmacy.controller.ParameterName.USER_PATRONYMIC;
import static com.epam.pharmacy.controller.ParameterName.USER_PHONE;
import static com.epam.pharmacy.controller.ParameterName.USER_ROLE;
import static com.epam.pharmacy.controller.ParameterName.USER_SEX;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> userData = ParamsMapCreator.create(request, USER_LOGIN, USER_PASSWORD, REPEAT_PASSWORD,
                USER_ROLE, USER_NAME, USER_LASTNAME, USER_PATRONYMIC, USER_BIRTHDAY_DATE, USER_SEX, USER_PHONE,
                USER_ADDRESS);
        HttpSession session = request.getSession();
        if (UserRole.ADMIN != session.getAttribute(CURRENT_USER_ROLE)) {
            userData.put(USER_ROLE, UserRole.CUSTOMER.name());
        }
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        Router router;
        String page = (String) session.getAttribute(AttributeName.CURRENT_PAGE);
        try {
            boolean isCreated = userService.create(userData);
            if (isCreated) {
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_REGISTRATION, true);
                if (session.getAttribute(CURRENT_USER_ROLE) == UserRole.GUEST) {
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

}
