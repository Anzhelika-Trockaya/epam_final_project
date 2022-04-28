package com.epam.pharmacy.model.service;

import com.epam.pharmacy.model.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService;
    public static ServiceProvider getInstance(){
        return instance;
    }
    private ServiceProvider(){
        userService=new UserServiceImpl();
    }

    public UserService getUserService() {
        return userService;
    }
}
