package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.AttributeName.USER_ID;

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String idString = request.getParameter(USER_ID);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        try {
            boolean deleted = userService.deleteById(idString);
            Router router = new Router(PagePath.USERS, Router.Type.REDIRECT);
            HttpSession session = request.getSession();
            if (deleted) {
                session.setAttribute(AttributeName.SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.USERS_DELETED_MESSAGE);
            } else {
                session.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.USERS_NOT_DELETED_MESSAGE);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the DeleteUserCommand", e);
            throw new CommandException("Exception in the DeleteUserCommand", e);
        }
    }
}
