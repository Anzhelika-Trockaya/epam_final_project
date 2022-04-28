package com.epam.pharmacy.controller.command.impl;

import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        ServiceProvider serviceProvider=ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        String page;
        HttpSession session = request.getSession();
        try {
            Optional<UserRole> userRoleOptional=userService.authenticate(login, password);
            if(userRoleOptional.isPresent()){
                session.setAttribute("user_name", login);
                session.setAttribute("user_role", userRoleOptional.get());
                page = PagePath.MAIN;
            } else{
                request.setAttribute("login_msg","incorrect login or pass");
                page= PagePath.INDEX;
            }
            session.setAttribute("current_page", page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(page);
    }
}

