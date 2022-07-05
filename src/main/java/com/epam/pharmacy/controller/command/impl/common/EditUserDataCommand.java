package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.USER_ADDRESS;
import static com.epam.pharmacy.controller.ParameterName.USER_BIRTHDAY_DATE;
import static com.epam.pharmacy.controller.ParameterName.USER_LASTNAME;
import static com.epam.pharmacy.controller.ParameterName.USER_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_PATRONYMIC;
import static com.epam.pharmacy.controller.ParameterName.USER_PHONE;
import static com.epam.pharmacy.controller.ParameterName.USER_SEX;

public class EditUserDataCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> userData = ParamsMapCreator.create(request, USER_NAME, USER_LASTNAME,
                USER_PATRONYMIC, USER_BIRTHDAY_DATE, USER_SEX, USER_PHONE, USER_ADDRESS);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        Router router = new Router(PagePath.USER_PAGE);
        try {
            HttpSession session = request.getSession();
            long id = (long) session.getAttribute(CURRENT_USER_ID);
            userData.put(ParameterName.USER_ID, Long.toString(id));
            Optional<User> oldUserOptional = userService.updateUserInfo(userData);
            ContentFiller contentFiller = ContentFiller.getInstance();
            if (oldUserOptional.isPresent()) {
                String fullName = userService.findUserFullName(id);
                session.setAttribute(CURRENT_USER_FULL_NAME, fullName);
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.USER_SUCCESSFUL_UPDATED);
                router.setTypeRedirect();
            } else {
                contentFiller.addDataToRequest(request, userData);
                request.setAttribute(SHOW_EDIT_FORM, true);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the EditUserDataCommand", e);
            throw new CommandException("Exception in the EditUserDataCommand", e);
        }
        return router;
    }
}
