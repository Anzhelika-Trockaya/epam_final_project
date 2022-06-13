package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.controller.Router;
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
            router = new Router(PagePath.HOME);
            if (isAdded) {
                session.setAttribute(AttributeName.SUCCESSFUL_ADDED, Boolean.TRUE.toString());
                router.setTypeRedirect();
            } else{
                RequestFiller.getInstance().addInternationalNames(request);
                request.setAttribute(AttributeName.FAILED, Boolean.TRUE);
            }
        } catch(ServiceException e){
            LOGGER.error("Exception in the AddMedicineToCartCommand", e);
            throw new CommandException("Exception in the AddMedicineToCartCommand", e);
        }
        return router;
    }
}
