package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoChangeUsersPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        addUsersList(request);
        return new Router(PagePath.USERS);

    }

    public void addUsersList(HttpServletRequest request) throws CommandException {
        try {
            ServiceProvider provider = ServiceProvider.getInstance();
            UserService userService = provider.getUserService();
            List<User> listUsers = userService.findAll();
            request.setAttribute(AttributeName.USERS_LIST, listUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception in the ChangeUsersCommand " + e);
            throw new CommandException("Exception in the ChangeUsersCommand ", e);
        }
    }
}
