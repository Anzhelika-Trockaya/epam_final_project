package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ID;
import static com.epam.pharmacy.controller.ParameterName.ORDER_MEDICINE_ID;
import static com.epam.pharmacy.controller.ParameterName.ORDER_MEDICINE_NUMBER;

public class AddMedicineToCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = request.getSession();
        long customerId = (long)session.getAttribute(CURRENT_USER_ID);
        String medicineId = request.getParameter(ORDER_MEDICINE_ID);
        String medicineQuantity = request.getParameter(ORDER_MEDICINE_NUMBER);
        Router router;
        try {
            boolean isAdded = orderService.addToCart(customerId, medicineId, medicineQuantity);
            router = new Router(PagePath.MEDICINES);
            if (isAdded) {
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.MEDICINES_ADDED_TO_CART);
                router.setTypeRedirect();
            } else{
                //fixme if pagination
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_NOT_ADDED_TO_CART);
            }
        } catch(ServiceException e){
            LOGGER.error("Exception in the AddMedicineToCartCommand", e);
            throw new CommandException("Exception in the AddMedicineToCartCommand", e);
        }
        return router;
    }
}
