package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.model.mapper.CustomRequestMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pharmacy.controller.ParameterName.*;

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
        String login = request.getParameter(LOGIN);
        userData.put(USER_LOGIN, login);
        String lastname = request.getParameter(LASTNAME);
        userData.put(USER_LASTNAME, lastname);
        String name = request.getParameter(NAME);
        userData.put(USER_NAME, name);
        String patronymic = request.getParameter(PATRONYMIC);
        userData.put(USER_PATRONYMIC, patronymic);
        String password = request.getParameter(PASSWORD);
        userData.put(USER_PASSWORD, password);
        String birthdayDate = request.getParameter(BIRTHDAY_DATE);
        userData.put(USER_BIRTHDAY_DATE, birthdayDate);
        String sex = request.getParameter(SEX);
        userData.put(USER_SEX, sex);
        String userRole = request.getParameter(USER_ROLE);
        userData.put(USER_ROLE, userRole);
        String phone = request.getParameter(PHONE);
        userData.put(USER_PHONE, phone);
        String address = request.getParameter(ADDRESS);
        userData.put(USER_ADDRESS, address);
        return userData;
    }
}
