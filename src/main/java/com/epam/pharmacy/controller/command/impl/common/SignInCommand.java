package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;

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
                    User user = userOptional.get();
                    if (User.State.BLOCKED == user.getState()) {
                        request.setAttribute(BLOCKED, true);
                        addSignInAttrsToRequest(request);
                        router.setPage(PagePath.SIGN_IN);
                    }
                session.setAttribute(AttributeName.CURRENT_USER_ID, user.getId());
                session.setAttribute(AttributeName.CURRENT_USER_ROLE, user.getRole());
                switch(user.getRole()){
                    case DOCTOR:
                        router.setPage(PagePath.PRESCRIPTIONS);
                        break;
                    case PHARMACIST:
                        router.setPage(PagePath.MEDICINES);
                        break;
                    default:
                        router.setPage(PagePath.HOME);
                        break;
                }
                router.setTypeRedirect();
            } else {
                request.setAttribute(INCORRECT_DATA, true);
                addSignInAttrsToRequest(request);
                router.setPage(PagePath.SIGN_IN);
            }
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void addSignInAttrsToRequest(HttpServletRequest request){
        String loginFromRequest = request.getParameter(ParameterName.LOGIN);
        request.setAttribute(AttributeName.LOGIN, loginFromRequest);
        String passwordFromRequest = request.getParameter(ParameterName.PASSWORD);
        request.setAttribute(AttributeName.PASSWORD, passwordFromRequest);
    }
}

