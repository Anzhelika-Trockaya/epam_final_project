package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.service.InternationalNameService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.pharmacy.controller.PropertyKey.INTERNATIONAL_NAMES_EDITED;
import static com.epam.pharmacy.controller.PropertyKey.INTERNATIONAL_NAMES_NOT_EDITED;

public class EditInternationalNameCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(ParameterName.NAME);
        String id = request.getParameter(ParameterName.INTERNATIONAL_NAME_ID);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        InternationalNameService internationalNameService = serviceProvider.getInternationalNameService();
        Router router;
        try {
            Optional<InternationalMedicineName> internationalNameOptional = internationalNameService.update(id, name);
            router = new Router(PagePath.INTERNATIONAL_NAMES_PAGE);
            if (internationalNameOptional.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, INTERNATIONAL_NAMES_EDITED);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, INTERNATIONAL_NAMES_NOT_EDITED);
                ContentFiller.getInstance().addInternationalNames(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the EditInternationalNameCommand", e);
            throw new CommandException("Exception in the EditInternationalNameCommand", e);
        }
        return router;
    }
}
