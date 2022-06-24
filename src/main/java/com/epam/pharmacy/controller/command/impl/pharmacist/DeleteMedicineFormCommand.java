package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.PropertyKey;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.InternationalNameService;
import com.epam.pharmacy.model.service.MedicineFormService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.pharmacy.controller.ParameterName.FORM_ID;

public class DeleteMedicineFormCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String idString = request.getParameter(FORM_ID);
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineFormService formService = provider.getMedicineFormService();
        try {
            boolean deleted = formService.delete(idString);
            Router router = new Router(PagePath.FORMS, Router.Type.REDIRECT);
            HttpSession session = request.getSession();
            if (deleted) {
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, PropertyKey.FORMS_DELETED);
            } else {
                session.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, PropertyKey.FORMS_NOT_DELETED);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the DeleteMedicineFormCommand", e);
            throw new CommandException("Exception in the DeleteMedicineFormCommand", e);
        }
    }
}
