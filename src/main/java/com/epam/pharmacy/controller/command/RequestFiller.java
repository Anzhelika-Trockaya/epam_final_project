package com.epam.pharmacy.controller.command;

import com.epam.pharmacy.controller.AttributeName;
import com.epam.pharmacy.controller.ParameterName;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.*;
import com.epam.pharmacy.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;

public class RequestFiller {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int START_QUANTITY = 0;
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
            LOGGER.error("Exception when fill international names ", e);
            throw new CommandException("Exception when fill international names ", e);
        }
    }

    public void addMedicinesData(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineService medicineService = provider.getMedicineService();
        try {
            Map<Long, Map<String, Object>> medicinesMap = medicineService.findAll();//fixme для покупателя только кол-во больше 0
            request.setAttribute(MEDICINES_DATA_MAP, medicinesMap);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill medicines ", e);
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
            LOGGER.error("Exception when fill manufacturers ", e);
            throw new CommandException("Exception when fill manufacturers", e);
        }
    }

    public void addPrescriptions(HttpServletRequest httpServletRequest) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        PrescriptionService prescriptionService = provider.getPrescriptionService();
        HttpSession session = httpServletRequest.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        long id = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            Map<Long, Map<String, String>> prescriptionsMap;
            switch (currentUserRole) {
                case DOCTOR:
                    prescriptionsMap = prescriptionService.findAllByDoctor(id);
                    int renewalRequestsQuantity = findNeedRenewal(prescriptionsMap);
                    httpServletRequest.setAttribute(AttributeName.RENEWAL_REQUESTS_QUANTITY, renewalRequestsQuantity);
                    break;
                case CUSTOMER:
                    prescriptionsMap = prescriptionService.findAllByCustomer(id);
                    break;
                default:
                    prescriptionsMap = null;
                    break;
            }
            if (prescriptionsMap != null) {
                httpServletRequest.setAttribute(AttributeName.PRESCRIPTIONS_MAP, prescriptionsMap);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill prescriptions ", e);
            throw new CommandException("Exception when fill prescriptions ", e);
        }
    }

    public void addPrescriptionRenewalRequests(HttpServletRequest httpServletRequest) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        PrescriptionService prescriptionService = provider.getPrescriptionService();
        HttpSession session = httpServletRequest.getSession();
        UserRole currentUserRole = (UserRole) session.getAttribute(CURRENT_USER_ROLE);
        long id = (long) session.getAttribute(CURRENT_USER_ID);
        try {
            Map<Long, Map<String, String>> prescriptionsMap;
            if (currentUserRole == UserRole.DOCTOR) {
                prescriptionsMap = prescriptionService.findRenewalRequestsByDoctor(id);
                httpServletRequest.setAttribute(AttributeName.PRESCRIPTIONS_MAP, prescriptionsMap);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill prescription renewal requests", e);
            throw new CommandException("Exception when fill prescription renewal requests", e);
        }
    }

    public void addUsersExceptCurrent(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            long currentUserId = (long) session.getAttribute(CURRENT_USER_ID);
            ServiceProvider provider = ServiceProvider.getInstance();
            UserService userService = provider.getUserService();
            List<User> listUsers = userService.findAllExceptId(currentUserId);
            request.setAttribute(AttributeName.USERS_LIST, listUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill users ", e);
            throw new CommandException("Exception when fill users ", e);
        }
    }

    public void addActiveCustomers(HttpServletRequest request) throws CommandException {
        try {
            ServiceProvider provider = ServiceProvider.getInstance();
            UserService userService = provider.getUserService();
            List<User> listUsers = userService.findActiveCustomers();
            request.setAttribute(AttributeName.CUSTOMERS_LIST, listUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill customers ", e);
            throw new CommandException("Exception when fill customers ", e);
        }
    }

    public void addCustomerPersonalData(HttpServletRequest request, User customer) {
        request.setAttribute(PRESCRIPTION_CUSTOMER_ID, customer.getId());
        request.setAttribute(CUSTOMER_LASTNAME, customer.getLastname());
        request.setAttribute(CUSTOMER_NAME, customer.getName());
        request.setAttribute(CUSTOMER_PATRONYMIC, customer.getPatronymic());
        request.setAttribute(CUSTOMER_SEX, customer.getSex());
        request.setAttribute(CUSTOMER_BIRTHDAY_DATE, customer.getBirthdayDate());
    }

    public void moveSessionAttributeToRequest(HttpServletRequest request, String attributeName) {
        HttpSession session = request.getSession();
        Object attributeValue = session.getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            session.removeAttribute(attributeName);
        }
    }

    public void addDataToRequest(HttpServletRequest request, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    private int findNeedRenewal(Map<Long, Map<String, String>> prescriptionsMap) {
        Map<String, String> currentParamsMap;
        int quantity=START_QUANTITY;
        for(Map.Entry<Long, Map<String, String>> prescription : prescriptionsMap.entrySet()){
            currentParamsMap=prescription.getValue();
            String needRenewal = currentParamsMap.get(ParameterName.NEED_RENEWAL);
            if(Boolean.parseBoolean(needRenewal)){
                quantity++;
            }
        }
        return quantity;
    }
}
