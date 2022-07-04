package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.InternationalNameService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.ParameterName.NAME;
import static com.epam.pharmacy.controller.PropertyKey.INTERNATIONAL_NAMES_ADDED;
import static com.epam.pharmacy.controller.PropertyKey.INTERNATIONAL_NAMES_NOT_ADDED;

public class AddInternationalNameCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(NAME);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        InternationalNameService internationalNameService = serviceProvider.getInternationalNameService();
        Router router;
        try {
            boolean isCreated = internationalNameService.create(name);
            router = new Router(PagePath.INTERNATIONAL_NAMES_PAGE);
            if (isCreated) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, INTERNATIONAL_NAMES_ADDED);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, INTERNATIONAL_NAMES_NOT_ADDED);
                ContentFiller.getInstance().addInternationalNames(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddInternationalNameCommand", e);
            throw new CommandException("Exception in the AddInternationalNameCommand", e);
        }
        return router;
    }
}
