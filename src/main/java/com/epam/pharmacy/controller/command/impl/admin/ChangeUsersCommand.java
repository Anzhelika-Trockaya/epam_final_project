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

import java.util.List;

public class ChangeUsersCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        try {
            List<User> listUsers = userService.findAllUsers();
            request.setAttribute(AttributeName.USERS_LIST, listUsers);
            return new Router(PagePath.USERS);
        } catch (ServiceException e) {
            throw new CommandException("Exception in the ChangeUsersCommand ", e);
        }
    }
}
