package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.RequestFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ManufacturerService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.PropertyKey.MANUFACTURERS_ADDED;
import static com.epam.pharmacy.controller.PropertyKey.MANUFACTURERS_NOT_ADDED;


public class AddManufacturerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(ParameterName.NAME);
        String country = request.getParameter(ParameterName.COUNTRY);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ManufacturerService manufacturerService = serviceProvider.getManufacturerService();
        Router router;
        try {
            boolean isCreated = manufacturerService.create(name, country);
            router = new Router(PagePath.MANUFACTURERS);
            if (isCreated) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, MANUFACTURERS_ADDED);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, MANUFACTURERS_NOT_ADDED);
                RequestFiller.getInstance().addManufacturers(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddManufacturerCommand", e);
            throw new CommandException("Exception in the AddManufacturerCommand", e);
        }
        return router;
    }
}
