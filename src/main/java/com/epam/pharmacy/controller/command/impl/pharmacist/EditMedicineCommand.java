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
import java.util.Optional;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE_UNIT;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_FORM_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_INTERNATIONAL_NAME_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_NAME;
import static com.epam.pharmacy.controller.PropertyKey.EDIT_MEDICINE_NOT_EDITED;
import static com.epam.pharmacy.controller.PropertyKey.EDIT_MEDICINE_SUCCESSFUL_EDITED;

/**
 * The type Edit medicine command.
 */
public class EditMedicineCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> medicineData = ParamsMapCreator.create(request, MEDICINE_ID, MEDICINE_NAME,
                MEDICINE_INTERNATIONAL_NAME_ID, MEDICINE_MANUFACTURER_ID, MEDICINE_FORM_ID, MEDICINE_DOSAGE,
                MEDICINE_DOSAGE_UNIT, MEDICINE_NEED_PRESCRIPTION, MEDICINE_NUMBER_IN_PACKAGE, MEDICINE_TOTAL_PACKAGES,
                MEDICINE_PRICE);
        String changeTotalValue = request.getParameter(CHANGE_TOTAL_VALUE);
        medicineData.put(MEDICINE_CHANGE_TOTAL_VALUE, changeTotalValue);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        Router router;
        String link = request.getParameter(OLD_IMAGE_PATH);
        if (link.isEmpty()) {
            MedicineImageUploader imageUploader = MedicineImageUploader.getInstance();
            link = imageUploader.uploadImage(request);
        }
        try {
            medicineData.put(MEDICINE_IMAGE_LINK, link);
            boolean isUpdated = medicineService.update(medicineData);
            if (isUpdated) {
                HttpSession session = request.getSession();
                session.setAttribute(TEMP_SUCCESSFUL_CHANGE_MESSAGE, EDIT_MEDICINE_SUCCESSFUL_EDITED);
                router = new Router(PagePath.MEDICINES_PAGE, Router.Type.REDIRECT);
            } else {
                request.setAttribute(FAILED_CHANGE_MESSAGE, EDIT_MEDICINE_NOT_EDITED);
                request.setAttribute(MEDICINE_ID, medicineData.get(MEDICINE_ID));
                updateTotalPackages(medicineData);
                ContentFiller contentFiller = ContentFiller.getInstance();
                contentFiller.addDataToRequest(request, medicineData);
                contentFiller.addManufacturers(request);
                contentFiller.addForms(request);
                contentFiller.addInternationalNames(request);
                router = new Router(PagePath.ADD_MEDICINE_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the EditMedicineCommand", e);
            throw new CommandException("Exception in the EditMedicineCommand", e);
        }
        return router;
    }

    private void updateTotalPackages(Map<String, String> medicineData) throws ServiceException {
        String idString = medicineData.get(MEDICINE_ID);
        long id = Long.parseLong(idString);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        Optional<Integer> totalOptional = medicineService.findTotalPackages(id);
        if (totalOptional.isPresent()) {
            medicineData.put(MEDICINE_TOTAL_PACKAGES, String.valueOf(totalOptional.get()));
        } else {
            LOGGER.warn("Exception when find total packages of medicine. id=" + id);
        }
    }
}
