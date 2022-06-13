package com.epam.pharmacy.controller.command.impl.customer;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.RequestFiller;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.GoHomePageCommand;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.pharmacy.controller.ParameterName.SEARCH_MEDICINE_NAME;

public class SearchMedicineCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        String name = request.getParameter(SEARCH_MEDICINE_NAME);
        try {
            List<Medicine> medicines = medicineService.findByName(name);
            RequestFiller requestFiller = RequestFiller.getInstance();
            requestFiller.addInternationalNames(request);
            requestFiller.addForms(request);
            requestFiller.addManufacturers(request);
            request.setAttribute(AttributeName.MEDICINES_LIST, medicines);
            request.setAttribute(AttributeName.SHOW_SEARCH_RESULT, true);
            return new Router(PagePath.HOME);
        } catch (ServiceException e) {
            LOGGER.error("Exception in the SearchMedicineCommand", e);
            throw new CommandException("Exception in the SearchMedicineCommand", e);
        }
    }
}
