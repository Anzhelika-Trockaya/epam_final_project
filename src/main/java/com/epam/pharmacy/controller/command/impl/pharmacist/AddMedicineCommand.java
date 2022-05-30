package com.epam.pharmacy.controller.command.impl.pharmacist;

import com.epam.pharmacy.controller.*;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.CommandType;
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
            boolean isCreated;
            if (!link.isEmpty()) {
                medicineData.put(MEDICINE_IMAGE_LINK, link);
                isCreated = medicineService.create(medicineData);
            } else {
                isCreated = false;
            }
            router = new Router(PagePath.ADD_MEDICINES);
            if (isCreated) {
                HttpSession session = request.getSession();
                session.setAttribute(AttributeName.SUCCESSFUL_ADDED, Boolean.TRUE.toString());
                router.setTypeRedirect();
            } else {
                addDataToRequest(request, medicineData);
                GoAddMedicinePageCommand command = (GoAddMedicinePageCommand) CommandType.GO_ADD_MEDICINE_PAGE.getCommand();
                command.fillRequest(request);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in the AddMedicineCommand" + e);
            throw new CommandException("Exception in the AddMedicineCommand", e);
        }
        return router;
    }

    private void addDataToRequest(HttpServletRequest request, Map<String, String> medicineData) {
        for (String key : medicineData.keySet()) {
            request.setAttribute(key, medicineData.get(key));
        }
    }

    private Map<String, String> createMedicineDataMap(HttpServletRequest request) {
        Map<String, String> medicineData = new HashMap<>();
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
        String amountInPart = request.getParameter(AMOUNT_IN_PART);
        medicineData.put(MEDICINE_AMOUNT_IN_PART, amountInPart);
        String partsInPackage = request.getParameter(PARTS_IN_PACKAGE);
        medicineData.put(MEDICINE_PARTS_IN_PACKAGE, partsInPackage);
        String totalParts = request.getParameter(TOTAL_PARTS);
        medicineData.put(MEDICINE_TOTAL_PARTS, totalParts);
        String price = request.getParameter(PRICE);
        medicineData.put(MEDICINE_PRICE, price);
        String ingredients = request.getParameter(INGREDIENTS);
        medicineData.put(MEDICINE_INGREDIENTS, ingredients);
        String instruction = request.getParameter(INSTRUCTION);
        medicineData.put(MEDICINE_INSTRUCTION, instruction);
        return medicineData;
    }
}
