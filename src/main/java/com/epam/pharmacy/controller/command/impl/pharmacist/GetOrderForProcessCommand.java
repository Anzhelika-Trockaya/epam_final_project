package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.*;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;


public class GetOrderForProcessCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        UserService userService = provider.getUserService();
        try {
            Router router = new Router(PagePath.ORDERS_PAGE);
            HttpSession session = request.getSession();
            long customerId = (long) session.getAttribute(CURRENT_USER_ID);
            Optional<Order> orderOptional = orderService.findNextPaidOrder(customerId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                Optional<User> customerOptional = userService.findById(order.getCustomerId());
                if (!customerOptional.isPresent()) {
                    LOGGER.warn("Customer not found. CustomerId=" + order.getCustomerId());
                    return router;
                }
                router = new Router(PagePath.ORDER_PAGE, Router.Type.REDIRECT);
                session.setAttribute(TEMP_CUSTOMER, customerOptional.get());
                session.setAttribute(TEMP_ORDER, order);
                List<Map<String, Object>> orderContent = orderService.findOrderContent(order.getId());
                session.setAttribute(TEMP_ORDER_CONTENT_LIST, orderContent);
            } else {
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addCurrentUserOrders(request);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GetOrderForProcessCommand", e);
            throw new CommandException("Exception in the GetOrderForProcessCommand", e);
        }
    }
}
