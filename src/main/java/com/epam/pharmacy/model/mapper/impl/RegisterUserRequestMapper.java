package com.epam.pharmacy.model.mapper.impl;

import com.epam.pharmacy.model.mapper.CustomRequestMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pharmacy.controller.ParameterName.*;

public class RegisterUserRequestMapper implements CustomRequestMapper {
    private static RegisterUserRequestMapper instance;

    private RegisterUserRequestMapper() {
    }

    public static RegisterUserRequestMapper getInstance() {
        if (instance == null) {
            instance = new RegisterUserRequestMapper();
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
        String userRole = request.getParameter(ROLE);
        userData.put(USER_ROLE, userRole);
        String phone = request.getParameter(PHONE);
        userData.put(USER_PHONE, phone);
        String address = request.getParameter(ADDRESS);
        userData.put(USER_ADDRESS, address);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);
        userData.put(REPEAT_PASSWORD, repeatPassword);
        return userData;
    }
}
