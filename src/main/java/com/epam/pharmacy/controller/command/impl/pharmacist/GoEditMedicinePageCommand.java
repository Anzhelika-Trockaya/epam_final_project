package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_ID;

public class GoEditMedicinePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String medicineId = request.getParameter(MEDICINE_ID);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        Router router;
        try {
            Optional<Medicine> medicineOptional = medicineService.findById(medicineId);
            if (medicineOptional.isPresent()) {
                request.setAttribute(AttributeName.MEDICINE, medicineOptional.get());
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addManufacturers(request);
                contentFiller.addForms(request);
                contentFiller.addInternationalNames(request);
                router = new Router(PagePath.ADD_MEDICINE_PAGE);
            } else {
                request.setAttribute(FAILED_CHANGE_MESSAGE, PropertyKey.MEDICINES_NOT_FOUND);
                HttpSession session = request.getSession();
                String currentPage = (String) session.getAttribute(CURRENT_PAGE);
                router = new Router(currentPage);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GoChangeMedicinePageCommand", e);
            throw new CommandException("Exception in the GoChangeMedicinePageCommand", e);
        }
        return router;
    }
}
