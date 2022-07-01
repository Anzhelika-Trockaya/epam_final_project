package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.service.MedicineFormService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.pharmacy.controller.PropertyKey.FORMS_EDITED;
import static com.epam.pharmacy.controller.PropertyKey.FORMS_NOT_EDITED;

public class EditMedicineFormCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(ParameterName.NAME);
        String unit = request.getParameter(ParameterName.FORM_UNIT);
        String id = request.getParameter(ParameterName.FORM_ID);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineFormService formService = serviceProvider.getMedicineFormService();
        Router router;
        try {
            Optional<MedicineForm> formOptional = formService.update(id, name, unit);
            router = new Router(PagePath.FORMS);
            if (formOptional.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_CHANGE_MESSAGE, FORMS_EDITED);
                router.setTypeRedirect();
            } else {
                request.setAttribute(AttributeName.FAILED_CHANGE_MESSAGE, FORMS_NOT_EDITED);
                ContentFiller.getInstance().addForms(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the EditMedicineFormCommand", e);
            throw new CommandException("Exception in the EditMedicineFormCommand", e);
        }
        return router;
    }
}
