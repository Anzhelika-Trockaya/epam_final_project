package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.USER_BIRTHDAY_DATE;
import static com.epam.pharmacy.controller.ParameterName.USER_LASTNAME;
import static com.epam.pharmacy.controller.ParameterName.USER_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_PATRONYMIC;
import static com.epam.pharmacy.controller.ParameterName.USER_ROLE;
import static com.epam.pharmacy.controller.ParameterName.USER_STATE;

/**
 * The type Search user command.
 */
public class SearchUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        Map<String, String> paramsMap = ParamsMapCreator.create(request, USER_ROLE, USER_NAME, USER_LASTNAME,
                USER_PATRONYMIC, USER_BIRTHDAY_DATE, USER_STATE);
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
}
