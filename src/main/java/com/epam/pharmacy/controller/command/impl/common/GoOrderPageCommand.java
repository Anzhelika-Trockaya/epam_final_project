package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.ORDER_ID;
import static com.epam.pharmacy.controller.ParameterName.TEMP_ORDER;

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
            return new Router(PagePath.ORDER, Router.Type.REDIRECT);
        } catch (CommandException e) {
            LOGGER.error("Exception in the GetOrderForProcessCommand", e);
            throw new CommandException("Exception in the GetOrderForProcessCommand", e);
        }
    }
}
