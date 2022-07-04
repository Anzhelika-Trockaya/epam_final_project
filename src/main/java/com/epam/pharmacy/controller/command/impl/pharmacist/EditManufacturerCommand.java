package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Manufacturer;
import com.epam.pharmacy.model.service.ManufacturerService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.pharmacy.controller.PropertyKey.*;

public class EditManufacturerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(ParameterName.NAME);
        String country = request.getParameter(ParameterName.COUNTRY);
        String id = request.getParameter(ParameterName.MANUFACTURER_ID);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ManufacturerService manufacturerService = serviceProvider.getManufacturerService();
        Router router;
        try {
            Optional<Manufacturer> manufacturerOptional = manufacturerService.update(id, name, country);
            router = new Router(PagePath.MANUFACTURERS_PAGE);
            if (manufacturerOptional.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, MANUFACTURERS_EDITED);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, MANUFACTURERS_NOT_EDITED);
                ContentFiller.getInstance().addManufacturers(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the EditManufacturerCommand", e);
            throw new CommandException("Exception in the EditManufacturerCommand", e);
        }
        return router;
    }
}
