package com.epam.pharmacy.controller;

import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.InternationalMedicineName;
import com.epam.pharmacy.model.entity.Manufacturer;
import com.epam.pharmacy.model.entity.Medicine;
import com.epam.pharmacy.model.entity.MedicineForm;
import com.epam.pharmacy.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RequestFiller {
    private static final Logger LOGGER = LogManager.getLogger();
    public static RequestFiller instance;
    private RequestFiller(){}

    public static RequestFiller getInstance() {
        if(instance==null){
            instance=new RequestFiller();
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
            LOGGER.error("Exception when fill request " + e);
            throw new CommandException("Exception when fill request  ", e);
        }
    }

    public void addInternationalNames(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        InternationalNameService internationalNameService = provider.getInternationalNameService();
        try {
            List<InternationalMedicineName> internationalNamesList = internationalNameService.findAll();
            request.setAttribute(AttributeName.INTERNATIONAL_NAMES_LIST, internationalNamesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill request " + e);
            throw new CommandException("Exception when fill request ", e);
        }
    }

    public void addMedicines(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineService medicineService = provider.getMedicineService();
        try {
            List<Medicine> medicinesList = medicineService.findAll();
            request.setAttribute(AttributeName.MEDICINES_LIST, medicinesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception when fill request " + e);
            throw new CommandException("Exception when fill request ", e);
        }
    }

    public void addManufacturers(HttpServletRequest request) throws CommandException {
        ServiceProvider provider = ServiceProvider.getInstance();
        MedicineFormService formService = provider.getMedicineFormService();
        ManufacturerService manufacturerService = provider.getManufacturerService();
        InternationalNameService internationalNameService = provider.getInternationalNameService();
        try {
            List<MedicineForm> formsList = formService.findAll();
            request.setAttribute(AttributeName.FORMS_LIST, formsList);
            List<Manufacturer> manufacturersList = manufacturerService.findAll();
            request.setAttribute(AttributeName.MANUFACTURERS_LIST, manufacturersList);
            List<InternationalMedicineName> internationalNamesList = internationalNameService.findAll();
            request.setAttribute(AttributeName.INTERNATIONAL_NAMES_LIST, internationalNamesList);
        } catch (ServiceException e) {
            LOGGER.error("Exception in the GoAddMedicinePageCommand " + e);
            throw new CommandException("Exception in the GoAddMedicinePageCommand ", e);
        }
    }
}
