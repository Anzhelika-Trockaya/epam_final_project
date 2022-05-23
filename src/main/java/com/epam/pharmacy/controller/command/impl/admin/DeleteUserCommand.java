package com.epam.pharmacy.controller.command.impl.admin;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class DeleteUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {//todo check it
        String idString = request.getParameter(ParameterName.USER_ID);//todo add to users.jsp
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        try{
            boolean deleted = userService.deleteById(idString);
            Router router=new Router(PagePath.USERS, Router.Type.REDIRECT);
            HttpSession session = request.getSession();
            session.setAttribute(AttributeName.FAILED, !deleted);
            List<User> listUsers = userService.findAll();
            session.setAttribute(AttributeName.USERS_LIST, listUsers);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
