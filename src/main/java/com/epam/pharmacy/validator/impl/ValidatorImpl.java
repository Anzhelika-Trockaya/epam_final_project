package com.epam.pharmacy.validator.impl;

import com.epam.pharmacy.validator.Validator;

import static com.epam.pharmacy.controller.Parameter.*;

import java.util.Map;

public class ValidatorImpl implements Validator {
    private static final String SEX_REGEX = "(MALE|FEMALE)";//fixme correct user_role
    private static final String USER_ROLE_REGEX = "(ADMIN|PHARMACIST|DOCTOR|CUSTOMER)";
    private static ValidatorImpl instance;
    private static final String USER_NAME_REGEX = "[A-Za-zА-Яа-яёЁ][A-Za-zА-Яа-яёЁ-]{0,44}";
    private static final String USER_LOGIN_REGEX = "[a-zA-Zа-яА-ЯёЁ0-9._-]{4,45}";
    private static final String USER_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,45}";
    private static final String DATE_REGEX = "[1-2]\\d{3}-[0-1]\\d-[0-3]\\d";
    private static final String PHONE_REGEX = "\\+375(33|29|25|44)\\d{7}";

    private ValidatorImpl() {
    }

    public static ValidatorImpl getInstance() {
        if (instance == null) {
            instance = new ValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isCorrectLogin(String login) {
        return login != null && login.matches(USER_LOGIN_REGEX);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return password != null && password.matches(USER_PASSWORD_REGEX);
    }

    @Override
    public boolean isCorrectName(String name) {
        return name != null && name.matches(USER_NAME_REGEX);
    }

    @Override
    public boolean isCorrectBirthdayDate(String birthdayDate) {
        return birthdayDate != null && birthdayDate.matches(DATE_REGEX);
    }

    @Override
    public boolean isCorrectSex(String sex) {
        return sex != null && sex.matches(SEX_REGEX);
    }

    @Override
    public boolean isCorrectPhone(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }

    @Override
    public boolean isCorrectAddress(String address) {
        return address != null && !address.isEmpty();
    }

    @Override
    public boolean isCorrectUserRole(String user_role) {
        return user_role != null && user_role.matches(USER_ROLE_REGEX);
    }

    @Override
    public boolean isCorrectRegisterData(Map<String, String> userData) {
        if (userData == null || userData.isEmpty()) {
            return false;
        }
        boolean result = true;
        String login = userData.get(USER_LOGIN);
        String password = userData.get(USER_PASSWORD);
        String lastname = userData.get(USER_LASTNAME);
        String name = userData.get(USER_NAME);
        String patronymic = userData.get(USER_PATRONYMIC);
        String birthdayDate = userData.get(USER_BIRTHDAY_DATE);
        String sex = userData.get(USER_SEX);
        String phone = userData.get(USER_PHONE);
        String address = userData.get(USER_ADDRESS);
        String role = userData.get(USER_ROLE);
        if (!isCorrectLogin(login)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect login");//fixme magic value
        } else if (!isCorrectPassword(password)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect password");
        } else if (!isCorrectName(lastname)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect lastname");
        } else if (!isCorrectName(name)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect name");
        } else if (!isCorrectName(patronymic)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect patronymic");
        } else if (!isCorrectBirthdayDate(birthdayDate)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect birthday date");
        } else if (!isCorrectSex(sex)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect sex");
        }else if (!isCorrectPhone(phone)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect phone");
        } else if (!isCorrectAddress(address)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect address");
        } else if (!isCorrectUserRole(role)) {
            result = false;
            userData.put(INCORRECT_DATA_INFO, "incorrect role");
        }
        return result;
    }
}
