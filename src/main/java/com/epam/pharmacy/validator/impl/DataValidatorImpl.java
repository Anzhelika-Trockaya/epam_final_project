package com.epam.pharmacy.validator.impl;

import com.epam.pharmacy.validator.DataValidator;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE_UNIT;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_FORM_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_INTERNATIONAL_NAME_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_BIRTHDAY_DATE;
import static com.epam.pharmacy.controller.ParameterName.USER_LASTNAME;
import static com.epam.pharmacy.controller.ParameterName.USER_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_PATRONYMIC;
import static com.epam.pharmacy.controller.ParameterName.USER_ROLE;
import static com.epam.pharmacy.controller.PropertyKey.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataValidatorImpl implements DataValidator {
    private static DataValidatorImpl instance;
    private static final String ID_REGEX = "[1-9]\\d{0,18}";
    private static final String USER_STATE_REGEX = "(ACTIVE|BLOCKED)";
    private static final String SEX_REGEX = "(MALE|FEMALE)";
    private static final String USER_ROLE_REGEX = "(ADMIN|PHARMACIST|DOCTOR|CUSTOMER)";
    private static final String ADDRESS_REGEX = "[a-zA-Z0-9а-яА-ЯёЁіІўЎ\\s/,_:;.'\"-]+";
    private static final String USER_NAME_REGEX = "[a-zA-Zа-яА-ЯёЁіІўЎ][a-zA-Zа-яА-ЯёЁіІўЎ-]{0,44}";
    private static final String USER_LOGIN_REGEX = "[a-zA-Z0-9а-яА-ЯёЁІіЎў._-]{4,45}";
    private static final String USER_PASSWORD_REGEX =
            "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zа-яёіў])(?=.*[A-ZА-ЯЁІЎ])[A-ZА-ЯЁІЎa-zа-яёіў0-9!@#$%^&*]{6,45}";
    private static final String DATE_REGEX = "[1-2]\\d{3}-[0-1]\\d-[0-3]\\d";
    private static final String PHONE_REGEX = "\\+375(33|29|25|44)\\d{7}";
    private static final String LANGUAGE_REGEX = "(be_BY|en_US)";
    private static final String QUANTITY_REGEX = "[1-9]\\d{0,8}";
    private static final String CHANGE_QUANTITY_REGEX = "0|(-?[1-9]\\d{0,8})";
    private static final String DOSAGE_UNIT_REGEX = "(MILLILITER|MILLIGRAM|GRAM|MICROGRAM|ME|NANOGRAM)";
    private static final String PRICE_REGEX = "\\d{1,20}(.\\d{2})?";
    private static final String INTERNATIONAL_NAME_REGEX = "[A-Z][a-zA-Z-\\s]{0,99}";
    private static final String MANUFACTURER_NAME_REGEX = "[A-ZА-ЯЁІЎ][a-zA-Z0-9а-яА-ЯёЁіІўЎ/,_:;.'\"-&\\s]{0,44}";
    private static final String COUNTRY_REGEX = "[A-ZА-ЯЁІЎ][a-zA-Zа-яА-ЯёЁіІўЎ.'\\s-]{0,44}";
    private static final String FORM_NAME_REGEX = "[a-zA-Zа-яА-ЯёЁіІўЎ/,_:;.'\"\\s-]{1,100}";
    private static final String FORM_UNIT_REGEX = "(PIECES|TABLES|MILLILITERS)";

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
    public boolean isCorrectBirthdayDate(String birthdayDateString) {
        boolean result = false;
        if (birthdayDateString != null && birthdayDateString.matches(DATE_REGEX)) {
            LocalDate today = LocalDate.now();
            LocalDate birthdayDate = LocalDate.parse(birthdayDateString);
            LocalDate birthdayThisYear = LocalDate.of(today.getYear(), birthdayDate.getMonth(), birthdayDate.getDayOfMonth());
            int age = today.getYear() - birthdayDate.getYear();
            if (birthdayThisYear.isAfter(today)) {
                age--;
            }
            if (age > 17) {
                result = true;
            }
        }
        return result;
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
        boolean result = isCorrectUserPersonalData(userData);
        String login = userData.get(USER_LOGIN);
        String password = userData.get(USER_PASSWORD);
        String repeat_password = userData.get(REPEAT_PASSWORD);
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
        if (!isCorrectUserRole(role)) {
            result = false;
            userData.put(INCORRECT_ROLE, REGISTRATION_INCORRECT_ROLE);
        }
        return result;
    }

    @Override
    public boolean isCorrectUserPersonalData(Map<String, String> userData) {
        if (userData == null || userData.isEmpty()) {
            return false;
        }
        boolean result = true;
        String lastname = userData.get(USER_LASTNAME);
        String name = userData.get(USER_NAME);
        String patronymic = userData.get(USER_PATRONYMIC);
        String birthdayDate = userData.get(USER_BIRTHDAY_DATE);
        String sex = userData.get(USER_SEX);
        String phone = userData.get(USER_PHONE);
        String address = userData.get(USER_ADDRESS);
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
        if (!isCorrectAddress(address)) {
            result = false;
            userData.put(INCORRECT_ADDRESS, REGISTRATION_INCORRECT_ADDRESS);
        }
        return result;
    }

    @Override
    public boolean isCorrectUserSearchParamsMap(Map<String, String> paramsMap) {
        String lastname = paramsMap.get(USER_LASTNAME);
        String name = paramsMap.get(USER_NAME);
        String patronymic = paramsMap.get(USER_PATRONYMIC);
        String birthdayDate = paramsMap.get(USER_BIRTHDAY_DATE);
        return (!paramsMap.isEmpty()) &&
                (lastname.isEmpty() || isCorrectName(lastname)) &&
                (name.isEmpty() || isCorrectName(name)) &&
                (patronymic.isEmpty() || isCorrectName(patronymic)) &&
                (birthdayDate.isEmpty() || isCorrectBirthdayDate(birthdayDate));
    }

    @Override
    public boolean isCorrectMedicineSearchParamsMap(HashMap<String, String> paramsMap) {
        String dosage = paramsMap.get(MEDICINE_DOSAGE);
        return (!paramsMap.isEmpty()) && (dosage.isEmpty() || isCorrectQuantity(dosage));
    }

    @Override
    public boolean isCorrectAddress(String address) {
        return address != null && address.matches(ADDRESS_REGEX);
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
        String numberInPackage = medicineData.get(MEDICINE_NUMBER_IN_PACKAGE);
        String totalPackages = medicineData.get(MEDICINE_TOTAL_PACKAGES);
        String price = medicineData.get(MEDICINE_PRICE);
        String instruction = medicineData.get(MEDICINE_INSTRUCTION);
        String ingredients = medicineData.get(MEDICINE_INGREDIENTS);
        String imageLink = medicineData.get(MEDICINE_IMAGE_LINK);
        String changeTotalValue = medicineData.get(MEDICINE_CHANGE_TOTAL_VALUE);
        if (changeTotalValue == null) {
            if (!isCorrectQuantity(totalPackages)) {
                result = false;
                medicineData.put(INCORRECT_TOTAL_PACKAGES, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
            }
        } else {
            if (!isCorrectChangeQuantity(changeTotalValue)) {
                result = false;
                medicineData.put(INCORRECT_CHANGE_TOTAL_VALUE, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
            }
        }
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
        if (!isCorrectQuantity(dosage)) {
            result = false;
            medicineData.put(INCORRECT_DOSAGE, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isCorrectQuantity(numberInPackage)) {
            result = false;
            medicineData.put(INCORRECT_NUMBER_IN_PACKAGE, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
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
        if (!isNotEmpty(imageLink)) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean isCorrectQuantity(String value) {
        return value != null && value.matches(QUANTITY_REGEX);
    }

    @Override
    public boolean isCorrectChangeQuantity(String value) {
        return value != null && value.matches(CHANGE_QUANTITY_REGEX);
    }

    @Override
    public boolean isCorrectDosageUnit(String unit) {
        return unit != null && unit.matches(DOSAGE_UNIT_REGEX);
    }

    @Override
    public boolean isCorrectPrice(String price) {
        return price != null && price.matches(PRICE_REGEX);
    }

    @Override
    public boolean isCorrectInternationalName(String name) {
        return name != null && name.matches(INTERNATIONAL_NAME_REGEX);
    }

    @Override
    public boolean isCorrectManufacturerName(String name) {
        return name != null && name.matches(MANUFACTURER_NAME_REGEX);
    }

    @Override
    public boolean isCorrectCountry(String country) {
        return country != null && country.matches(COUNTRY_REGEX);
    }

    @Override
    public boolean isCorrectFormName(String name) {
        return name != null && name.matches(FORM_NAME_REGEX);
    }

    @Override
    public boolean isCorrectFormUnit(String unit) {
        return unit != null && unit.matches(FORM_UNIT_REGEX);
    }

    @Override
    public boolean isCorrectPrescriptionData(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        boolean result = true;
        String quantity = data.get(PRESCRIPTION_QUANTITY);
        String dosage = data.get(PRESCRIPTION_DOSAGE);
        String dosageUnit = data.get(PRESCRIPTION_DOSAGE_UNIT);
        String internationalNameId = data.get(PRESCRIPTION_INTERNATIONAL_NAME_ID);
        String formId = data.get(PRESCRIPTION_FORM_ID);
        if (!isCorrectQuantity(quantity)) {
            result = false;
            data.put(INCORRECT_QUANTITY, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isCorrectQuantity(dosage)) {
            result = false;
            data.put(INCORRECT_DOSAGE, ADDING_MEDICINE_INCORRECT_NOT_INTEGER);
        }
        if (!isNotEmpty(dosageUnit)) {
            result = false;
            data.put(INCORRECT_DOSAGE_UNIT, ADDING_MEDICINE_INCORRECT_REQUIRED);
        }
        if (!isNotEmpty(internationalNameId)) {
            result = false;
            data.put(INCORRECT_INTERNATIONAL_NAME, ADDING_MEDICINE_INCORRECT_REQUIRED);
        }
        if (!isNotEmpty(formId)) {
            result = false;
            data.put(INCORRECT_FORM, ADDING_MEDICINE_INCORRECT_REQUIRED);
        }
        return result;
    }
}
