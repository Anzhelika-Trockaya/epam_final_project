package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoChangeMedicinesPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        fillRequestFormsInternationalNamesMedicines(request);
        return new Router(PagePath.MEDICINES);
    }

    public void fillRequestFormsInternationalNamesMedicines(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineFormService formService = provider.getMedicineFormService();
        InternationalNameService internationalNameService = provider.getInternationalNameService();
        MedicineService medicineService = provider.getMedicineService();
        try {
            List<MedicineForm> formsList = formService.findAll();
            request.setAttribute(AttributeName.FORMS_LIST, formsList);
            List<InternationalMedicineName> internationalNamesList = internationalNameService.findAll();
            request.setAttribute(AttributeName.INTERNATIONAL_NAMES_LIST, internationalNamesList);
            List<Medicine> medicinesList = medicineService.findAll();
            request.setAttribute(AttributeName.MEDICINES_LIST, medicinesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GoChangeMedicinesPageCommand " + e);
            throw new CommandException("Exception in the GoChangeMedicinesPageCommand ", e);
        }
    }
}
