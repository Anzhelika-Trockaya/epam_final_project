package com.epam.pharmacy.validator;

import java.util.Map;

public interface DataValidator {
    boolean isCorrectLogin(String login);

    boolean isCorrectPassword(String password);

    boolean isCorrectName(String name);

    boolean isCorrectBirthdayDate(String birthdayDate);

    boolean isCorrectSex(String sex);

    boolean isCorrectPhone(String phone);

    boolean isNotEmpty(String address);

    boolean isCorrectUserRole(String user_role);

    boolean isCorrectLanguage(String language);

    boolean isCorrectRegisterData(Map<String, String> userData);

    boolean isCorrectId(String id);

    boolean isCorrectState(String state);

    boolean isCorrectMedicineData(Map<String, String> medicineData);

    boolean isCorrectInteger(String value);

    boolean isCorrectDosageUnit(String unit);

    boolean isCorrectPrice(String price);
}