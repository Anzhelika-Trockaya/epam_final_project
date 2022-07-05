package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.ParameterName.ORDER_ID;

public class CompleteOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String idString = request.getParameter(ORDER_ID);
        long orderId = Long.parseLong(idString);
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        try {
            boolean completed = orderService.completeOrder(orderId);
            Router router = new Router(PagePath.ORDER_PAGE);
            HttpSession session = request.getSession();
            if (completed) {
                router.setTypeRedirect();
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addOrderContentToSession(session, orderId);
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.ORDER_NOT_COMPLETED_MSG);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the CompleteOrderCommand", e);
            throw new CommandException("Exception in the CompleteOrderCommand", e);
        }
    }
}
