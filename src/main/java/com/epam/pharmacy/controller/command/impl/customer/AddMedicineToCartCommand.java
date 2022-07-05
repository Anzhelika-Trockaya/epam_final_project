package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.OrderService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.PRESCRIPTION_ID;

/**
 * The type Add medicine to cart command.
 */
public class AddMedicineToCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = request.getSession();
        long customerId = (long) session.getAttribute(CURRENT_USER_ID);
        String medicineId = request.getParameter(ORDER_MEDICINE_ID);
        Router router = new Router(PagePath.MEDICINES_PAGE);
        try {
            Map<String, String> positionParams = ParamsMapCreator.create(request,
                    ORDER_MEDICINE_ID, ORDER_MEDICINE_NUMBER);
            Optional<Medicine> medicineOptional = medicineService.findById(medicineId);
            if (!medicineOptional.isPresent()) {
                LOGGER.warn("Medicine with id=" + medicineId + " not found");
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_NOT_ADDED_TO_CART);
                return router;
            }
            Medicine medicine = medicineOptional.get();
            if (medicine.needPrescription()) {
                String prescriptionId = request.getParameter(PRESCRIPTION_ID);
                positionParams.put(PRESCRIPTION_ID, prescriptionId);
                boolean isAdded = orderService.addToCartWithPrescription(customerId, positionParams);
                if(isAdded){
                    router.setPage(PagePath.PRESCRIPTIONS_PAGE);
                    router.setTypeRedirect();
                    session.setAttribute(TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.MEDICINES_ADDED_TO_CART);
                } else{
                    request.setAttribute(FAILED_CHANGE_MESSAGE, positionParams.get(FAILED_CHANGE_MESSAGE));
                }
            } else {
                boolean isAdded = orderService.addToCartWithoutPrescription(customerId, positionParams);
                if (isAdded) {
                    session.setAttribute(TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.MEDICINES_ADDED_TO_CART);
                    router.setTypeRedirect();
                } else {
                    request.setAttribute(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_NOT_ADDED_TO_CART);
                }
            }

        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddMedicineToCartCommand", e);
            throw new CommandException("Exception in the AddMedicineToCartCommand", e);
        }
        return router;
    }
}
