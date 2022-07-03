package com.epam.pharmacy.controller.command.impl.customer;

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

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ID;

public class DepositToUserAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String valueString = request.getParameter(ParameterName.VALUE);
        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            boolean changed = userService.depositToCustomerAccount(customerId, valueString);
            Router router = new Router(PagePath.BALANCE);
            if (changed) {
                router.setTypeRedirect();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE,
                        PropertyKey.BALANCE_ACCOUNT_BALANCE_CHANGED);
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE,
                        PropertyKey.BALANCE_INCORRECT_VALUE);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the DepositToUserAccountCommand", e);
            throw new CommandException("Exception in the DepositToUserAccountCommand", e);
        }
    }
}
