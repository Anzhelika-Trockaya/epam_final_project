package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.service.MedicineFormService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoAddMedicinePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineFormService formService = provider.getMedicineFormService();
        try {
            List<MedicineForm> formsList = formService.findAll();
            request.setAttribute(AttributeName.FORMS_LIST, formsList);
            //todo
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GoAddMedicinePageCommand " + e);
            throw new CommandException("Exception in the GoAddMedicinePageCommand ", e);
        }
        return new Router(PagePath.ADD_MEDICINES);
    }
}
