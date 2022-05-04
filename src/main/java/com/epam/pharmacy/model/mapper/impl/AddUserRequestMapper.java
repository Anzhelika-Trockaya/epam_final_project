package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.controller.Parameter;
import com.epam.pharmacy.model.entity.UserRole;
import com.epam.pharmacy.model.mapper.CustomRequestMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class AddUserRequestMapper implements CustomRequestMapper {
    private static AddUserRequestMapper instance;

    private AddUserRequestMapper() {
    }

    public static AddUserRequestMapper getInstance() {
        if (instance == null) {
            instance = new AddUserRequestMapper();
        }
        return instance;
    }

    @Override
    public Map<String, String> mapRequest(HttpServletRequest request) {
        Map<String, String> userData = new HashMap<>();
        String login = request.getParameter(Parameter.LOGIN);
        userData.put(Parameter.USER_LOGIN, login);
        String lastname = request.getParameter(Parameter.LASTNAME);
        userData.put(Parameter.USER_LASTNAME, lastname);
        String name = request.getParameter(Parameter.NAME);
        userData.put(Parameter.USER_NAME, name);
        String patronymic = request.getParameter(Parameter.PATRONYMIC);
        userData.put(Parameter.USER_PATRONYMIC, patronymic);
        String password = request.getParameter(Parameter.PASSWORD);
        userData.put(Parameter.USER_PASSWORD, password);
        String birthdayDate = request.getParameter(Parameter.BIRTHDAY_DATE);
        userData.put(Parameter.USER_BIRTHDAY_DATE, birthdayDate);
        String sex = request.getParameter(Parameter.SEX);
        userData.put(Parameter.USER_SEX, sex);
        String userRole = request.getParameter(Parameter.USER_ROLE);
        userData.put(Parameter.USER_ROLE, userRole);
        String phone = request.getParameter(Parameter.PHONE);
        userData.put(Parameter.USER_PHONE, phone);
        String address = request.getParameter(Parameter.ADDRESS);
        userData.put(Parameter.USER_ADDRESS, address);
        return userData;
    }
}
