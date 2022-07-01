package com.epam.pharmacy.controller.command.impl.doctor;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.ServiceProvider;
import com.epam.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GoAddPrescriptionPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        String customerId = request.getParameter(ParameterName.CUSTOMER_ID);
        long customerIdValue = Long.parseLong(customerId);
        UserService userService = serviceProvider.getUserService();
        Optional<User> customerOptional;
        try {
            customerOptional = userService.findById(customerIdValue);
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GoAddPrescriptionPageCommand. CustomerId=" + customerId, e);
            throw new CommandException("Exception in the GoAddPrescriptionPageCommand. CustomerId=" + customerId, e);
        }
        if (customerOptional.isPresent()) {
            User customer = customerOptional.get();
            ContentFiller contentFiller = ContentFiller.getInstance();
            contentFiller.addInternationalNames(request);
            contentFiller.addCustomerPersonalData(request, customer);
        } else {
            LOGGER.warn("Customer with id=" + customerId + " not found.");
            throw new CommandException("Customer with id=" + customerId + " not found.");
        }
        return new Router(PagePath.ADD_PRESCRIPTION);
    }
}
