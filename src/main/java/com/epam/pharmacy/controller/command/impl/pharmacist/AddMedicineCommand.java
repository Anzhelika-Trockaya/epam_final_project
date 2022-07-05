package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.controller.command.ParamsMapCreator;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.impl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE_UNIT;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_FORM_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_INTERNATIONAL_NAME_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_NAME;

public class AddMedicineCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> medicineData = ParamsMapCreator.create(request, MEDICINE_ID, MEDICINE_NAME,
                MEDICINE_INTERNATIONAL_NAME_ID, MEDICINE_MANUFACTURER_ID, MEDICINE_FORM_ID, MEDICINE_DOSAGE,
                MEDICINE_DOSAGE_UNIT, MEDICINE_NEED_PRESCRIPTION, MEDICINE_NUMBER_IN_PACKAGE, MEDICINE_TOTAL_PACKAGES,
                MEDICINE_PRICE);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        Router router;
        MedicineImageUploader imageUploader = MedicineImageUploader.getInstance();
        String link = imageUploader.uploadImage(request);
        try {
            medicineData.put(MEDICINE_IMAGE_LINK, link);
            boolean isCreated = medicineService.create(medicineData);
            router = new Router(PagePath.ADD_MEDICINE_PAGE);
            if (isCreated) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.TEMP_SUCCESSFUL_ADDED, true);
                router.setTypeRedirect();
            } else {
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addDataToRequest(request, medicineData);
                contentFiller.addManufacturers(request);
                contentFiller.addForms(request);
                contentFiller.addInternationalNames(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddMedicineCommand", e);
            throw new CommandException("Exception in the AddMedicineCommand", e);
        }
        return router;
    }
}
