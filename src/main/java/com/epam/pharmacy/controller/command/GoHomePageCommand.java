package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoHomePageCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        fillRequestInternationalNames(request);
        return new Router(PagePath.HOME);
    }

    public void fillRequestInternationalNames(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        InternationalNameService internationalNameService = provider.getInternationalNameService();
        try {
            List<InternationalMedicineName> internationalNamesList = internationalNameService.findAll();
            request.setAttribute(AttributeName.INTERNATIONAL_NAMES_LIST, internationalNamesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GoHomePageCommand " + e);
            throw new CommandException("Exception in the GoHomePageCommand ", e);
        }
    }
}
