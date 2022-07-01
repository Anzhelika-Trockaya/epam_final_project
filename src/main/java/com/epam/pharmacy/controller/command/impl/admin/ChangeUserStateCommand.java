package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.AttributeName.USER_ID;
import static com.epam.pharmacy.controller.AttributeName.USER_STATE;

public class ChangeUserStateCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String idString = request.getParameter(USER_ID);
        String stateString = request.getParameter(USER_STATE);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        try {
            boolean changed = userService.changeState(idString, stateString);
            Router router = new Router(PagePath.USERS);
            if (changed) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.USERS_STATE_CHANGED_MESSAGE);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.USERS_STATE_NOT_CHANGED_MESSAGE);
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addUsersExceptCurrent(request);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the ChangeUserStateCommand", e);
            throw new CommandException("Exception in the ChangeUserStateCommand", e);
        }
    }
}
