package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.ParameterName.ORDER_ID;

/**
 * The type Go order page command.
 */
public class GoOrderPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String orderIdString = request.getParameter(ORDER_ID);
        long orderId = Long.parseLong(orderIdString);
        ContentFiller contentFiller = ContentFiller.getInstance();
        try {
            HttpSession session= request.getSession();
            contentFiller.addOrderContentToSession(session, orderId);
            return new Router(PagePath.ORDER_PAGE, Router.Type.REDIRECT);
        } catch (CommandException e) {
            LOGGER.error("Exception in the GetOrderForProcessCommand", e);
            throw new CommandException("Exception in the GetOrderForProcessCommand", e);
        }
    }
}
