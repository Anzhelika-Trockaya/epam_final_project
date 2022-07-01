package com.epam.pharmacy.controller.command.impl.common;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.PagePath;
import com.epam.pharmacy.controller.Router;
import com.epam.pharmacy.controller.command.Command;
import com.epam.pharmacy.controller.command.ContentFiller;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.service.MedicineService;
import com.epam.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE_UNIT;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_FORM_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_INTERNATIONAL_NAME_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_NAME;

public class SearchMedicinesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        MedicineService medicineService = serviceProvider.getMedicineService();
        Map<String, String> paramsMap = createSearchParamsMap(request, MEDICINE_NAME,
                MEDICINE_INTERNATIONAL_NAME_ID, MEDICINE_FORM_ID, MEDICINE_DOSAGE, MEDICINE_DOSAGE_UNIT);
        Router router = new Router(PagePath.MEDICINES);
        try {
            HttpSession session = request.getSession();
            UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
            Map<Long, Map<String, Object>> medicinesData;
            if (UserRole.CUSTOMER == currentUserRole) {
                long customerId = (long) session.getAttribute(CURRENT_USER_ID);
                medicinesData = medicineService.findByParamsForCustomer(customerId, new HashMap<>(paramsMap));
            } else {
                medicinesData = medicineService.findByParams(new HashMap<>(paramsMap));
            }
            request.setAttribute(MEDICINES_DATA_MAP, medicinesData);
            request.setAttribute(AttributeName.SHOW_SEARCH_RESULT, true);
            ContentFiller contentFiller = ContentFiller.getInstance();
            contentFiller.addDataToRequest(request, paramsMap);
            contentFiller.addInternationalNames(request);
            contentFiller.addForms(request);
            return router;
        } catch (ServiceException e) {
            LOGGER.error("Exception in the SearchMedicineCommand", e);
            throw new CommandException("Exception in the SearchMedicineCommand", e);
        }
    }

    private Map<String, String> createSearchParamsMap(HttpServletRequest request, String... paramNames) {
        Map<String, String> params = new HashMap<>();
        String currentParamValue;
        for (String paramName : paramNames) {
            currentParamValue = request.getParameter(paramName);
            params.put(paramName, currentParamValue);
        }
        return params;
    }
}
