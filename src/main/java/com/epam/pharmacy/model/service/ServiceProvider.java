package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.service.impl.MedicineFormServiceImpl;
import com.epam.pharmacy.model.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService;
    private final MedicineFormService medicineFormService;
    public static ServiceProvider getInstance(){
        return instance;
    }
    private ServiceProvider(){
        userService=new UserServiceImpl();
        medicineFormService=new MedicineFormServiceImpl();
    }

    public UserService getUserService() {
        return userService;
    }

    public MedicineFormService getMedicineFormService() {
        return medicineFormService;
    }
}
