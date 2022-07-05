package com.epam.pharmacy.controller.command.impl.doctor;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.CURRENT_USER_ID;
import static com.epam.pharmacy.controller.ParameterName.*;

public class AddPrescriptionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> data = ParamsMapCreator.create(request, PRESCRIPTION_INTERNATIONAL_NAME_ID,
                PRESCRIPTION_CUSTOMER_ID, PRESCRIPTION_QUANTITY, PRESCRIPTION_VALIDITY, PRESCRIPTION_UNIT,
                PRESCRIPTION_DOSAGE, PRESCRIPTION_DOSAGE_UNIT, CUSTOMER_LASTNAME, CUSTOMER_NAME, CUSTOMER_PATRONYMIC,
                CUSTOMER_BIRTHDAY_DATE, CUSTOMER_SEX);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        PrescriptionService prescriptionService = serviceProvider.getPrescriptionService();
        Router router;
        try {
            HttpSession session = request.getSession();
            long doctorId = (long) session.getAttribute(CURRENT_USER_ID);
            boolean isCreated = prescriptionService.create(doctorId, data);
            if (isCreated) {
                router = new Router(PagePath.CUSTOMERS_PAGE);
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.PRESCRIPTIONS_ADDED);
                router.setTypeRedirect();
            } else {
                router = new Router(PagePath.ADD_PRESCRIPTION_PAGE);
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.ADD_PRESCRIPTION_NOT_ADDED);
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addDataToRequest(request, data);
                contentFiller.addInternationalNames(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddPrescriptionCommand", e);
            throw new CommandException("Exception in the AddPrescriptionCommand", e);
        }
        return router;
    }
}
