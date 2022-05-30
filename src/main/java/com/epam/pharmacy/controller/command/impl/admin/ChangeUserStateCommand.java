package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.epam.pharmacy.controller.AttributeName.USER_ID;
import static com.epam.pharmacy.controller.AttributeName.USER_STATE;

public class ChangeUserStateCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String idString = request.getParameter(USER_ID);
        String stateString = request.getParameter(USER_STATE);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        try {
            boolean changed = userService.changeState(idString, stateString);
            Router router = new Router(PagePath.USERS, Router.Type.REDIRECT);
            HttpSession session = request.getSession();
            if (changed) {
                session.setAttribute(AttributeName.SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.USERS_STATE_CHANGED_MESSAGE);
            } else {
                session.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.USERS_STATE_NOT_CHANGED_MESSAGE);
            }
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
