package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.AttributeName;
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

public class SignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        HttpSession session = request.getSession();
        try {
            Optional<UserRole> userRoleOptional = userService.authenticate(login, password);
            Router router=new Router();
            if (userRoleOptional.isPresent()) {
                session.setAttribute(AttributeName.LOGIN, login);//fixme зачем
                session.setAttribute(AttributeName.USER_ROLE, userRoleOptional.get());
                router.setPage(PagePath.HOME);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED, true);//fixme magic value
                router.setPage(PagePath.SIGN_IN);
            }
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

