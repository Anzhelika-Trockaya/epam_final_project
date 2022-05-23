package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
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
            Optional<User> userOptional = userService.authenticate(login, password);
            Router router=new Router();
            if (userOptional.isPresent()) {
                session.setAttribute(AttributeName.CURRENT_USER_ID, userOptional.get().getId());
                session.setAttribute(AttributeName.CURRENT_USER_ROLE, userOptional.get().getRole());
                router.setPage(PagePath.HOME);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED, true);//fixme magic value
                String loginFromRequest = request.getParameter(ParameterName.LOGIN);
                request.setAttribute(AttributeName.LOGIN, loginFromRequest);
                String passwordFromRequest = request.getParameter(ParameterName.PASSWORD);
                request.setAttribute(AttributeName.PASSWORD, passwordFromRequest);
                router.setPage(PagePath.SIGN_IN);
            }
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

