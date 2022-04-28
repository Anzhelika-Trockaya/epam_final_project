package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.model.dao.DaoProvider;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public Optional<UserRole> authenticate(String login, String password)  throws ServiceException {
        //fixme validation login pass + md5
        DaoProvider daoProvider=DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao();
        try {
            return userDao.authenticate(login, password);
        } catch(DaoException daoException){
            //todo? log?
            throw new ServiceException(daoException);
        }
    }
}
