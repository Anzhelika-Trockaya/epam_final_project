package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
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
        Map<String, String> medicineData = createMedicineDataMap(request);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        Router router;
        MedicineImageUploader imageUploader = MedicineImageUploader.getInstance();
        String link = imageUploader.uploadImage(request);
        try {
            medicineData.put(MEDICINE_IMAGE_LINK, link);
            boolean isCreated = medicineService.create(medicineData);
            router = new Router(PagePath.ADD_MEDICINE);
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

    public Map<String, String> createMedicineDataMap(HttpServletRequest request) {
        Map<String, String> medicineData = new HashMap<>();
        String id = request.getParameter(MEDICINE_ID);
        medicineData.put(MEDICINE_ID, id);
        String name = request.getParameter(NAME);
        medicineData.put(MEDICINE_NAME, name);
        String internationalNameId = request.getParameter(INTERNATIONAL_NAME);
        medicineData.put(MEDICINE_INTERNATIONAL_NAME_ID, internationalNameId);
        String manufacturerId = request.getParameter(MANUFACTURER);
        medicineData.put(MEDICINE_MANUFACTURER_ID, manufacturerId);
        String formId = request.getParameter(FORM);
        medicineData.put(MEDICINE_FORM_ID, formId);
        String dosage = request.getParameter(DOSAGE);
        medicineData.put(MEDICINE_DOSAGE, dosage);
        String dosageUnit = request.getParameter(DOSAGE_UNIT);
        medicineData.put(MEDICINE_DOSAGE_UNIT, dosageUnit);
        String needPrescription = request.getParameter(NEED_PRESCRIPTION);
        medicineData.put(MEDICINE_NEED_PRESCRIPTION, needPrescription);
        String numberInPackage = request.getParameter(NUMBER_IN_PACKAGE);
        medicineData.put(MEDICINE_NUMBER_IN_PACKAGE, numberInPackage);
        String totalPackages = request.getParameter(TOTAL_PACKAGES);
        medicineData.put(MEDICINE_TOTAL_PACKAGES, totalPackages);
        String price = request.getParameter(PRICE);
        medicineData.put(MEDICINE_PRICE, price);
        return medicineData;
    }
}
