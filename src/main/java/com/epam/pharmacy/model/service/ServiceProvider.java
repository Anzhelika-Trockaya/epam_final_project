package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.service.impl.*;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService;
    private final MedicineFormService medicineFormService;
    private final InternationalNameService internationalNameService;
    private final ManufacturerService manufacturerService;
    private final MedicineService medicineService;

    public static ServiceProvider getInstance() {
        return instance;
    }

    private ServiceProvider() {
        userService = new UserServiceImpl();
        medicineFormService = new MedicineFormServiceImpl();
        internationalNameService = new InternationalNameServiceImpl();
        manufacturerService = new ManufacturerServiceImpl();
        medicineService = new MedicineServiceImpl();
    }

    public UserService getUserService() {
        return userService;
    }

    public MedicineFormService getMedicineFormService() {
        return medicineFormService;
    }

    public InternationalNameService getInternationalNameService() {
        return internationalNameService;
    }

    public ManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    public MedicineService getMedicineService() {
        return medicineService;
    }
}
