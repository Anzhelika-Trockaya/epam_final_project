package com.epam.pharmacy.controller.command.impl.customer;

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

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ID;
import static com.epam.pharmacy.controller.ParameterName.*;

public class ChangeMedicineQuantityInCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String medicineIdString = request.getParameter(MEDICINE_ID);
        String prescriptionIdString = request.getParameter(PRESCRIPTION_ID);
        String quantityString = request.getParameter(QUANTITY);
        long medicineId = Long.parseLong(medicineIdString);
        long prescriptionId = Long.parseLong(prescriptionIdString);
        int quantity = Integer.parseInt(quantityString);
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        Router router = new Router(PagePath.CART_PAGE);
        try {
            boolean result = orderService.changePositionQuantityInCart(medicineId,
                    prescriptionId, quantity, customerId);
            if (result) {
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.CART_FAILED_CHANGE_POSITION);
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addCartContent(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the ChangeMedicineQuantityInCartCommand", e);
            throw new CommandException("Exception in the ChangeMedicineQuantityInCartCommand", e);
        }
        return router;
    }
}
