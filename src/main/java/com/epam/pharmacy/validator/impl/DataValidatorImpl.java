package com.epam.pharmacy.validator.impl;

import com.epam.pharmacy.validator.DataValidator;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.PropertyKey.*;

import java.util.Map;

public class DataValidatorImpl implements DataValidator {
    private static DataValidatorImpl instance;
    private static final String ID_REGEX = "[1-9]\\d{0,18}";
    private static final String USER_STATE_REGEX = "(ACTIVE|BLOCKED)";
    private static final String SEX_REGEX = "(MALE|FEMALE)";
    private static final String USER_ROLE_REGEX = "(ADMIN|PHARMACIST|DOCTOR|CUSTOMER)";
    private static final String USER_NAME_REGEX = "[a-zA-Zа-яА-ЯёЁ][a-zA-Zа-яА-ЯёЁ-]{0,44}";
    private static final String USER_LOGIN_REGEX = "[a-zA-Z0-9а-яА-ЯёЁ._-]{4,45}";
    private static final String USER_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ])[A-ZА-ЯЁa-zа-яё0-9!@#$%^&*]{6,45}";
    private static final String DATE_REGEX = "[1-2]\\d{3}-[0-1]\\d-[0-3]\\d";
    private static final String PHONE_REGEX = "\\+375(33|29|25|44)\\d{7}";
    private static final String LANGUAGE_REGEX = "(be_BY|en_US)";
    private static final String INTEGER_REGEX = "[1-9]\\d{0,9}";
    private static final String DOSAGE_UNIT_REGEX = "(MILLILITER|MILLIGRAM|GRAM|MICROGRAM|ME|NANOGRAM)";
    private static final String PRICE_REGEX = "\\d{1,20}(.\\d{2})?";

    private DataValidatorImpl() {
    }

    public static DataValidatorImpl getInstance() {
        if (instance == null) {
            instance = new DataValidatorImpl();
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
    public boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public boolean isCorrectUserRole(String user_role) {
        return user_role != null && user_role.matches(USER_ROLE_REGEX);
    }

    @Override
    public boolean isCorrectLanguage(String language) {
        return language != null && language.matches(LANGUAGE_REGEX);
    }

    @Override
    public boolean isCorrectRegisterData(Map<String, String> userData) {
        if (userData == null || userData.isEmpty()) {
            return false;
        }
        boolean result = true;
        String login = userData.get(USER_LOGIN);
        String password = userData.get(USER_PASSWORD);
        String repeat_password = userData.get(REPEAT_PASSWORD);
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
            userData.put(INCORRECT_LOGIN, REGISTRATION_INCORRECT_LOGIN);
        }
        if (!isCorrectPassword(password)) {
            result = false;
            userData.put(INCORRECT_PASSWORD, REGISTRATION_INCORRECT_PASSWORD);
        } else {
            if (!password.equals(repeat_password)) {
                result = false;
                userData.put(INCORRECT_REPEAT_PASSWORD, REGISTRATION_INCORRECT_REPEAT_PASSWORD);
            }
        }
        if (!isCorrectName(lastname)) {
            result = false;
            userData.put(INCORRECT_LASTNAME, REGISTRATION_INCORRECT_LASTNAME);
        }
        if (!isCorrectName(name)) {
            result = false;
            userData.put(INCORRECT_NAME, REGISTRATION_INCORRECT_NAME);
        }
        if (!isCorrectName(patronymic)) {
            result = false;
            userData.put(INCORRECT_PATRONYMIC, REGISTRATION_INCORRECT_PATRONYMIC);
        }
        if (!isCorrectBirthdayDate(birthdayDate)) {
            result = false;
            userData.put(INCORRECT_BIRTHDAY_DATE, REGISTRATION_INCORRECT_BIRTHDAY_DATE);
        }
        if (!isCorrectSex(sex)) {
            result = false;
            userData.put(INCORRECT_SEX, REGISTRATION_INCORRECT_SEX);
        }
        if (!isCorrectPhone(phone)) {
            result = false;
            userData.put(INCORRECT_PHONE, REGISTRATION_INCORRECT_PHONE);
        }
        if (!isNotEmpty(address)) {
            result = false;
            userData.put(INCORRECT_ADDRESS, REGISTRATION_INCORRECT_ADDRESS);
        }
        if (!isCorrectUserRole(role)) {
            result = false;
            userData.put(INCORRECT_ROLE, REGISTRATION_INCORRECT_ROLE);
        }
        return result;
    }

    @Override
    public boolean isCorrectId(String id) {
        return id != null && id.matches(ID_REGEX);
    }

    @Override
    public boolean isCorrectState(String state) {
        return state != null && state.matches(USER_STATE_REGEX);
    }

    @Override
    public boolean isCorrectMedicineData(Map<String, String> medicineData) {
        if (medicineData == null || medicineData.isEmpty()) {
            return false;
        }
        boolean result = true;
        String name = medicineData.get(MEDICINE_NAME);
        String internationalNameId = medicineData.get(MEDICINE_INTERNATIONAL_NAME_ID);
        String manufacturerId = medicineData.get(MEDICINE_MANUFACTURER_ID);
        String formId = medicineData.get(MEDICINE_FORM_ID);
        String dosage = medicineData.get(MEDICINE_DOSAGE);
        String dosageUnit = medicineData.get(MEDICINE_DOSAGE_UNIT);
        String amountInPart = medicineData.get(MEDICINE_AMOUNT_IN_PART);
        String partsInPackage = medicineData.get(MEDICINE_PARTS_IN_PACKAGE);
        String totalParts = medicineData.get(MEDICINE_TOTAL_PARTS);
        String price = medicineData.get(MEDICINE_PRICE);
        String instruction = medicineData.get(MEDICINE_INSTRUCTION);
        String ingredients = medicineData.get(MEDICINE_INGREDIENTS);
        if (!isNotEmpty(name)) {
            result = false;
            medicineData.put(INCORRECT_NAME, ADDING_MEDICINE_INCORRECT_REQUIRED);
        }
        if (!isCorrectId(internationalNameId)) {
            result = false;
            medicineData.put(INCORRECT_INTERNATIONAL_NAME, ADDING_MEDICINE_INCORRECT_INTERNATIONAL_NAME);
        }
        if (!isCorrectId(manufacturerId)) {
            result = false;
            medicineData.put(INCORRECT_MANUFACTURER, ADDING_MEDICINE_INCORRECT_MANUFACTURER);
        }
        if (!isCorrectId(formId)) {
            result = false;
            medicineData.put(INCORRECT_FORM, ADDING_MEDICINE_INCORRECT_FORM);
        }
        if (!isCorrectInteger(dosage)) {
            result = false;
            medicineData.put(INCORRECT_DOSAGE, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isCorrectInteger(amountInPart)) {
            result = false;
            medicineData.put(INCORRECT_AMOUNT_IN_PART, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isCorrectInteger(partsInPackage)) {
            result = false;
            medicineData.put(INCORRECT_PARTS_IN_PACKAGE, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isCorrectInteger(totalParts)) {
            result = false;
            medicineData.put(INCORRECT_TOTAL_PARTS, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isCorrectDosageUnit(dosageUnit)) {
            result = false;
            medicineData.put(INCORRECT_DOSAGE_UNIT, ADDING_MEDICINE_INCORRECT_DOSAGE_UNIT);
        }
        if (!isCorrectPrice(price)) {
            result = false;
            medicineData.put(INCORRECT_PRICE, ADDING_MEDICINE_INCORRECT_PRICE);
        }
        if (!isNotEmpty(ingredients)) {
            result = false;
            medicineData.put(INCORRECT_INGREDIENTS, ADDING_MEDICINE_INCORRECT_REQUIRED);
        }
        if (!isNotEmpty(instruction)) {
            result = false;
            medicineData.put(INCORRECT_INSTRUCTION, ADDING_MEDICINE_INCORRECT_REQUIRED);
        }
        return result;
    }

    @Override
    public boolean isCorrectInteger(String value) {
        return value != null && value.matches(INTEGER_REGEX);
    }

    @Override
    public boolean isCorrectDosageUnit(String unit) {
        return unit != null && unit.matches(DOSAGE_UNIT_REGEX);
    }

    @Override
    public boolean isCorrectPrice(String price) {
        return price != null && price.matches(PRICE_REGEX);
    }


}
