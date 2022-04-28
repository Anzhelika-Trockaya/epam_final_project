package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.dao.impl.UserDaoImpl;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private final UserDao userDao;
    public static DaoProvider getInstance(){
        return instance;
    }
    private DaoProvider(){
        userDao=new UserDaoImpl();
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
