package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_ID;
import static com.epam.pharmacy.controller.ParameterName.PRESCRIPTION_ID;

public class DeletePositionFromCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String medicineIdString = request.getParameter(MEDICINE_ID);
        String prescriptionIdString = request.getParameter(PRESCRIPTION_ID);
        long medicineId = Long.parseLong(medicineIdString);
        long prescriptionId = Long.parseLong(prescriptionIdString);
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        Router router = new Router(PagePath.CART);
        try {
            boolean result = orderService.deletePositionFromCart(medicineId, prescriptionId, customerId);
            if (result) {
                router.setTypeRedirect();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.CART_DELETED);
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.CART_NOT_DELETED);
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addCartContent(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the DeletePositionFromCartCommand", e);
            throw new CommandException("Exception in the DeletePositionFromCartCommand", e);
        }
        return router;
    }
}
