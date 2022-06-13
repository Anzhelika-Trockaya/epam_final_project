package com.epam.pharmacy.controller;

import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.*;
import com.epam.pharmacy.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RequestFiller {
    private static final Logger LOGGER = LogManager.getLogger();
    public static RequestFiller instance;

    private RequestFiller() {
    }

    public static RequestFiller getInstance() {
        if (instance == null) {
            instance = new RequestFiller();
        }
        return instance;
    }

    public void addForms(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineFormService formService = provider.getMedicineFormService();
        try {
            List<MedicineForm> formsList = formService.findAll();
            request.setAttribute(AttributeName.FORMS_LIST, formsList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill forms ", e);
            throw new CommandException("Exception when fill forms  ", e);
        }
    }

    public void addInternationalNames(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        InternationalNameService internationalNameService = provider.getInternationalNameService();
        try {
            List<InternationalMedicineName> internationalNamesList = internationalNameService.findAll();
            request.setAttribute(AttributeName.INTERNATIONAL_NAMES_LIST, internationalNamesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill international names " , e);
            throw new CommandException("Exception when fill international names ", e);
        }
    }

    public void addMedicines(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineService medicineService = provider.getMedicineService();
        try {
            List<Medicine> medicinesList = medicineService.findAll();
            request.setAttribute(AttributeName.MEDICINES_LIST, medicinesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill medicines " , e);
            throw new CommandException("Exception when fill medicines ", e);
        }
    }

    public void addManufacturers(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        ManufacturerService manufacturerService = provider.getManufacturerService();
        try {
            List<Manufacturer> manufacturersList = manufacturerService.findAll();
            request.setAttribute(AttributeName.MANUFACTURERS_LIST, manufacturersList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill manufacturers " , e);
            throw new CommandException("Exception when fill manufacturers", e);
        }
    }

    public void addUsers(HttpServletRequest request) throws CommandException {
        try {
            ServiceProvider provider = ServiceProvider.getInstance();
            UserService userService = provider.getUserService();
            List<User> listUsers = userService.findAll();
            request.setAttribute(AttributeName.USERS_LIST, listUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill users " , e);
            throw new CommandException("Exception when fill users ", e);
        }
    }

    public void moveSessionAttributeToRequest(HttpServletRequest request, String attributeName) {
        HttpSession session = request.getSession();
        Object attributeValue = session.getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            session.removeAttribute(attributeName);
        }
    }
}
