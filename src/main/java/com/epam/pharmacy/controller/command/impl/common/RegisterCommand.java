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
import static com.epam.pharmacy.controller.PropertyKey.*;
import static com.epam.pharmacy.controller.AttributeName.*;

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
                request.getSession().setAttribute(AttributeName.SUCCESSFUL_REGISTRATION, true);
                if (session.getAttribute(AttributeName.CURRENT_USER_ROLE)==null) {
                    page = PagePath.SIGN_IN;
                }
                router.setTypeRedirect();
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
        //fixme delete method
        // addIncorrectDataInfo(request, userData);
        for(String key: userData.keySet()) {
            request.setAttribute(key, userData.get(key));
        }
    }

    private void addIncorrectDataInfo(HttpServletRequest request, Map<String, String> userData) {
        for(String key: userData.keySet()) {
            switch (userData.get(key)){
                case REGISTRATION_INCORRECT_LASTNAME: {
                    request.setAttribute(INCORRECT_LASTNAME, REGISTRATION_INCORRECT_LASTNAME);
                    break;
                }
                case REGISTRATION_INCORRECT_NAME:{
                    request.setAttribute(INCORRECT_NAME, REGISTRATION_INCORRECT_NAME);
                    break;
                }
                case REGISTRATION_INCORRECT_PATRONYMIC:{
                    request.setAttribute(INCORRECT_PATRONYMIC, REGISTRATION_INCORRECT_PATRONYMIC);
                    break;
                }
                case REGISTRATION_INCORRECT_LOGIN:{
                    request.setAttribute(INCORRECT_LOGIN, REGISTRATION_INCORRECT_LOGIN);
                    break;
                }
                case REGISTRATION_INCORRECT_PASSWORD:{
                    request.setAttribute(INCORRECT_PASSWORD, REGISTRATION_INCORRECT_PASSWORD);
                    break;
                }
                case REGISTRATION_INCORRECT_SEX:{
                    request.setAttribute(INCORRECT_SEX, REGISTRATION_INCORRECT_SEX);
                    break;
                }
                case REGISTRATION_INCORRECT_BIRTHDAY_DATE:{
                    request.setAttribute(INCORRECT_BIRTHDAY_DATE, REGISTRATION_INCORRECT_BIRTHDAY_DATE);
                    break;
                }
                case REGISTRATION_INCORRECT_PHONE:{
                    request.setAttribute(INCORRECT_PHONE, REGISTRATION_INCORRECT_PHONE);
                    break;
                }
                case REGISTRATION_INCORRECT_ADDRESS:{
                    request.setAttribute(INCORRECT_ADDRESS, REGISTRATION_INCORRECT_ADDRESS);
                    break;
                }
                case REGISTRATION_INCORRECT_ROLE:{
                    request.setAttribute(INCORRECT_ROLE, REGISTRATION_INCORRECT_ROLE);
                    break;
                }
                case REGISTRATION_INCORRECT_REPEAT_PASSWORD:{
                    request.setAttribute(INCORRECT_REPEAT_PASSWORD, REGISTRATION_INCORRECT_REPEAT_PASSWORD);
                    break;
                }
            }
        }
    }

}
