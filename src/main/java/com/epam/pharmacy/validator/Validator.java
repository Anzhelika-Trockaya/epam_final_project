package com.epam.pharmacy.validator;

import com.epam.pharmacy.model.entity.User;

import java.util.Map;

public interface Validator {
    boolean isCorrectLogin(String login);

    boolean isCorrectPassword(String password);

    boolean isCorrectName(String name);

    boolean isCorrectBirthdayDate(String birthdayDate);

    boolean isCorrectSex(String sex);

    boolean isCorrectPhone(String phone);

    boolean isCorrectAddress(String address);

    boolean isCorrectUserRole(String user_role);

    boolean isCorrectLanguage(String language);

    boolean isCorrectRegisterData(Map<String, String> userData);

    boolean isCorrectId(String id);

    boolean isCorrectState(String state);
}
