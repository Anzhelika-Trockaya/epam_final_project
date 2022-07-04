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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char DELIMITER = ' ';

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        HttpSession session = request.getSession();
        try {
            Optional<User> userOptional = userService.authenticate(login, password);
            Router router = new Router();
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (User.State.BLOCKED == user.getState()) {
                    request.setAttribute(BLOCKED, true);
                    addSignInAttrsToRequest(request);
                    router.setPage(PagePath.SIGN_IN_PAGE);
                }
                session.setAttribute(AttributeName.CURRENT_USER_ID, user.getId());
                session.setAttribute(AttributeName.CURRENT_USER_ROLE, user.getRole());
                StringBuilder fullNameBuilder = new StringBuilder(user.getLastname());
                fullNameBuilder.append(DELIMITER).append(user.getName()).append(DELIMITER).append(user.getPatronymic());
                String fullName = fullNameBuilder.toString();
                session.setAttribute(AttributeName.CURRENT_USER_FULL_NAME, fullName);
                switch (user.getRole()) {
                    case DOCTOR:
                        router.setPage(PagePath.PRESCRIPTIONS_PAGE);
                        break;
                    case CUSTOMER:
                        BigDecimal userBalance = userService.findUserBalance(user.getId());
                        session.setAttribute(AttributeName.CURRENT_USER_BALANCE, userBalance);
                    case PHARMACIST:
                        router.setPage(PagePath.MEDICINES_PAGE);
                        break;
                    default:
                        router.setPage(PagePath.USERS_PAGE);
                        break;
                }
                router.setTypeRedirect();
            } else {
                request.setAttribute(INCORRECT_DATA, true);
                addSignInAttrsToRequest(request);
                router.setPage(PagePath.SIGN_IN_PAGE);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the SignInCommand", e);
            throw new CommandException("Exception in the SignInCommand", e);
        }
    }

    private void addSignInAttrsToRequest(HttpServletRequest request) {
        String loginFromRequest = request.getParameter(ParameterName.LOGIN);
        request.setAttribute(AttributeName.LOGIN, loginFromRequest);
        String passwordFromRequest = request.getParameter(ParameterName.PASSWORD);
        request.setAttribute(AttributeName.PASSWORD, passwordFromRequest);
    }
}

