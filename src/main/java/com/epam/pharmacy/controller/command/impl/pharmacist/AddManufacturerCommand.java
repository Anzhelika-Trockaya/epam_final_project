package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.ManufacturerService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.PropertyKey.MANUFACTURERS_ADDED;
import static com.epam.pharmacy.controller.PropertyKey.MANUFACTURERS_NOT_ADDED;


/**
 * The type Add manufacturer command.
 */
public class AddManufacturerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(ParameterName.NAME).trim();
        String country = request.getParameter(ParameterName.COUNTRY);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ManufacturerService manufacturerService = serviceProvider.getManufacturerService();
        Router router;
        try {
            boolean isCreated = manufacturerService.create(name, country);
            router = new Router(PagePath.MANUFACTURERS_PAGE);
            if (isCreated) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, MANUFACTURERS_ADDED);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, MANUFACTURERS_NOT_ADDED);
                request.setAttribute(AttributeName.NAME, name);
                request.setAttribute(AttributeName.COUNTRY, country);
                ContentFiller.getInstance().addManufacturers(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddManufacturerCommand", e);
            throw new CommandException("Exception in the AddManufacturerCommand", e);
        }
        return router;
    }
}
