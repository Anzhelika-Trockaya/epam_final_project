package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;

public class ChangePasswordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> passwordData = ParamsMapCreator.create(request, OLD_PASSWORD, NEW_PASSWORD, REPEAT_PASSWORD);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        Router router = new Router(PagePath.USER_PAGE);
        try {
            HttpSession session = request.getSession();
            long id = (long) session.getAttribute(CURRENT_USER_ID);
            boolean result = userService.updatePassword(id, passwordData);
            if (result) {
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.USER_PASSWORD_CHANGED);
                router.setTypeRedirect();
            } else {
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addDataToRequest(request, passwordData);
                request.setAttribute(SHOW_CHANGE_PASSWORD_FORM, true);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the ChangePasswordCommand", e);
            throw new CommandException("Exception in the ChangePasswordCommand", e);
        }
        return router;
    }
}
