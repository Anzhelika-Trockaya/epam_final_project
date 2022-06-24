package com.epam.pharmacy.validator;

import java.util.HashMap;
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

    boolean isCorrectAddress(String address);

    boolean isCorrectId(String id);

    boolean isCorrectState(String state);

    boolean isCorrectMedicineData(Map<String, String> medicineData);

    boolean isCorrectQuantity(String value);

    boolean isCorrectChangeQuantity(String value);

    boolean isCorrectDosageUnit(String unit);

    boolean isCorrectPrice(String price);

    boolean isCorrectInternationalName(String name);

    boolean isCorrectManufacturerName(String name);

    boolean isCorrectCountry(String country);

    boolean isCorrectFormName(String name);

    boolean isCorrectFormUnit(String unit);

    boolean isCorrectPrescriptionData(Map<String, String> data);

    boolean isCorrectUserPersonalData(Map<String, String> userData);

    boolean isCorrectUserSearchParamsMap(Map<String, String> paramsMap);

    boolean isCorrectMedicineSearchParamsMap(HashMap<String, String> paramsMap);
}
