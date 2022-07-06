package com.epam.pharmacy.validator.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.epam.pharmacy.controller.AttributeName.*;
import static com.epam.pharmacy.controller.AttributeName.MEDICINE_CHANGE_TOTAL_VALUE;
import static com.epam.pharmacy.controller.ParameterName.*;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_DOSAGE_UNIT;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_FORM_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_INTERNATIONAL_NAME_ID;
import static com.epam.pharmacy.controller.ParameterName.MEDICINE_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_ADDRESS;
import static com.epam.pharmacy.controller.ParameterName.USER_BIRTHDAY_DATE;
import static com.epam.pharmacy.controller.ParameterName.USER_LASTNAME;
import static com.epam.pharmacy.controller.ParameterName.USER_LOGIN;
import static com.epam.pharmacy.controller.ParameterName.USER_NAME;
import static com.epam.pharmacy.controller.ParameterName.USER_PASSWORD;
import static com.epam.pharmacy.controller.ParameterName.USER_PATRONYMIC;
import static com.epam.pharmacy.controller.ParameterName.USER_PHONE;
import static com.epam.pharmacy.controller.ParameterName.USER_ROLE;
import static com.epam.pharmacy.controller.ParameterName.USER_SEX;
import static com.epam.pharmacy.controller.ParameterName.USER_STATE;
import static org.testng.Assert.*;

public class DataValidatorImplTest {
    private DataValidatorImpl dataValidator = DataValidatorImpl.getInstance();

    @Test
    public void testIsCorrectLogin() {
        String correctLogin = "doctor";
        Assert.assertTrue(dataValidator.isCorrectLogin(correctLogin));
    }

    @Test
    public void testIsCorrectLoginFalse() {
        String incorrectLogin = "doc";
        Assert.assertFalse(dataValidator.isCorrectLogin(incorrectLogin));
    }

    @Test
    public void testIsCorrectPassword() {
        String correctPassword = "Pass1*";
        Assert.assertTrue(dataValidator.isCorrectPassword(correctPassword));
    }

    @Test
    public void testIsCorrectPasswordFalse() {
        String incorrectPassword = "Pass1";
        Assert.assertFalse(dataValidator.isCorrectPassword(incorrectPassword));
    }

    @Test
    public void testIsCorrectUserName() {
        String correctName = "Анжаліка";
        Assert.assertTrue(dataValidator.isCorrectUserName(correctName));
    }

    @Test
    public void testIsCorrectUserNameFalse() {
        String incorrectName = "-";
        Assert.assertFalse(dataValidator.isCorrectUserName(incorrectName));
    }

    @Test
    public void testIsCorrectBirthdayDate() {
        String correct = "1993-02-24";
        Assert.assertTrue(dataValidator.isCorrectBirthdayDate(correct));
    }

    @Test
    public void testIsCorrectBirthdayDateFalse() {
        String incorrect = "1993-02-44";
        Assert.assertFalse(dataValidator.isCorrectBirthdayDate(incorrect));
    }

    @Test
    public void testIsCorrectSex() {
        String correct = "MALE";
        Assert.assertTrue(dataValidator.isCorrectSex(correct));
    }

    @Test
    public void testIsCorrectSexFalse() {
        String incorrect = "M";
        Assert.assertFalse(dataValidator.isCorrectSex(incorrect));
    }

    @Test
    public void testIsCorrectPhone() {
        String correct = "+375336987452";
        Assert.assertTrue(dataValidator.isCorrectPhone(correct));
    }
    @Test
    public void testIsCorrectPhoneFalse() {
        String incorrect = "+375006987452";
        Assert.assertFalse(dataValidator.isCorrectPhone(incorrect));
    }

    @Test
    public void testIsCorrectUserRole() {
        String correct = "CUSTOMER";
        Assert.assertTrue(dataValidator.isCorrectUserRole(correct));
    }

    @Test
    public void testIsCorrectUserRoleFalse() {
        String incorrect = "";
        Assert.assertFalse(dataValidator.isCorrectUserRole(incorrect));
    }

    @Test
    public void testIsCorrectLanguage() {
        String correct = "be_BY";
        Assert.assertTrue(dataValidator.isCorrectLanguage(correct));
    }

    @Test
    public void testIsCorrectLanguageFalse() {
        String incorrect = "b_BY";
        Assert.assertFalse(dataValidator.isCorrectLanguage(incorrect));
    }

    @Test
    public void testIsCorrectRegisterData() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(USER_LASTNAME, "Троцкая");
        correctData.put(USER_NAME, "Анжаліка");
        correctData.put(USER_PATRONYMIC, "Віктараўна");
        correctData.put(USER_STATE, "ACTIVE");
        correctData.put(USER_ROLE, "DOCTOR");
        correctData.put(USER_SEX, "FEMALE");
        correctData.put(USER_LOGIN, "customer");
        correctData.put(USER_PASSWORD, "Customer!2");
        correctData.put(REPEAT_PASSWORD, "Customer!2");
        correctData.put(USER_BIRTHDAY_DATE, "1993-02-24");
        correctData.put(USER_PHONE, "+375333116737");
        correctData.put(USER_ADDRESS, "РБ, г.Гродна, вул.Курчатава, д.100, кв.12");
        Assert.assertTrue(dataValidator.isCorrectRegisterData(correctData));
    }

    @Test
    public void testIsCorrectRegisterDataFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(USER_LASTNAME, "Троцкая1");
        incorrectData.put(USER_NAME, "Анжаліка2");
        incorrectData.put(USER_PATRONYMIC, "Віктараўна3");
        incorrectData.put(USER_STATE, "AC");
        incorrectData.put(USER_ROLE, "DOCT");
        incorrectData.put(USER_SEX, "FEMAL");
        incorrectData.put(USER_LOGIN, "cus");
        incorrectData.put(USER_PASSWORD, "!2");
        incorrectData.put(REPEAT_PASSWORD, "Cuser!2");
        incorrectData.put(USER_BIRTHDAY_DATE, "1993-02-00");
        incorrectData.put(USER_PHONE, "+37533311");
        incorrectData.put(USER_ADDRESS, "");
        Assert.assertFalse(dataValidator.isCorrectRegisterData(incorrectData));
    }

    @Test
    public void testIsCorrectUserPersonalData() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(USER_LASTNAME, "Станчык");
        correctData.put(USER_NAME, "Аляксандр");
        correctData.put(USER_PATRONYMIC, "Іванавіч");
        correctData.put(USER_SEX, "MALE");
        correctData.put(USER_BIRTHDAY_DATE, "1968-12-04");
        correctData.put(USER_PHONE, "+375448965478");
        correctData.put(USER_ADDRESS, "РБ, Гродненская вобл., Воранаўскі раён, в.Нача, вул.Камсамольская д.10");
        Assert.assertTrue(dataValidator.isCorrectUserPersonalData(correctData));
    }

    @Test
    public void testIsCorrectUserPersonalDataFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(USER_LASTNAME, "1Станчык");
        incorrectData.put(USER_NAME, "");
        incorrectData.put(USER_PATRONYMIC, "");
        incorrectData.put(USER_SEX, "M");
        incorrectData.put(USER_BIRTHDAY_DATE, "19681304");
        incorrectData.put(USER_PHONE, "+375118965478");
        incorrectData.put(USER_ADDRESS, "");
        Assert.assertFalse(dataValidator.isCorrectUserPersonalData(incorrectData));
    }

    @Test
    public void testIsCorrectUserSearchParamsMap() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(USER_LASTNAME, "Тр");
        correctData.put(USER_NAME, "Анж");
        correctData.put(USER_PATRONYMIC, "в");
        correctData.put(USER_BIRTHDAY_DATE, "1993-02-24");
        Assert.assertTrue(dataValidator.isCorrectUserSearchParamsMap(correctData));
    }

    @Test
    public void testIsCorrectUserSearchParamsMapFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(USER_LASTNAME, "4");
        incorrectData.put(USER_NAME, "f5");
        incorrectData.put(USER_PATRONYMIC, "Fgh5");
        incorrectData.put(USER_BIRTHDAY_DATE, "0996-05-23");
        Assert.assertFalse(dataValidator.isCorrectUserSearchParamsMap(incorrectData));
    }

    @Test
    public void testIsCorrectMedicineSearchParamsMap() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(MEDICINE_NAME, "азі");
        correctData.put(MEDICINE_DOSAGE, "500");
        Assert.assertTrue(dataValidator.isCorrectMedicineSearchParamsMap(correctData));
    }

    @Test
    public void testIsCorrectMedicineSearchParamsMapFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(MEDICINE_NAME, "азі");
        incorrectData.put(MEDICINE_DOSAGE, "3.5");
        Assert.assertFalse(dataValidator.isCorrectMedicineSearchParamsMap(incorrectData));
    }

    @Test
    public void testIsCorrectDepositValue() {
        String correct = "100";
        Assert.assertTrue(dataValidator.isCorrectDepositValue(correct));
    }

    @Test
    public void testIsCorrectDepositValueFalse() {
        String incorrect = "100.01";
        Assert.assertFalse(dataValidator.isCorrectDepositValue(incorrect));
    }

    @Test
    public void testIsCorrectAddress() {
        String correct = "РБ, Гродненская вобл., Воранаўскі раён, в.Нача, д.42";
        Assert.assertTrue(dataValidator.isCorrectAddress(correct));
    }

    @Test
    public void testIsCorrectAddressFalse() {
        String incorrect = "";
        Assert.assertFalse(dataValidator.isCorrectAddress(incorrect));
    }

    @Test
    public void testIsCorrectId() {
        String correct = "1234567890123654789";
        Assert.assertTrue(dataValidator.isCorrectId(correct));
    }

    @Test
    public void testIsCorrectIdFalse() {
        String incorrect = "12345678901236547891";
        Assert.assertFalse(dataValidator.isCorrectId(incorrect));
    }

    @Test
    public void testIsCorrectUserState() {
        String correct = "BLOCKED";
        Assert.assertTrue(dataValidator.isCorrectUserState(correct));
    }

    @Test
    public void testIsCorrectUserStateFalse() {
        String incorrect = "STATE";
        Assert.assertFalse(dataValidator.isCorrectUserState(incorrect));
    }

    @Test
    public void testIsCorrectMedicineData() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(MEDICINE_NAME, "Азітраміцын");
        correctData.put(MEDICINE_INTERNATIONAL_NAME_ID, "125");
        correctData.put(MEDICINE_MANUFACTURER_ID, "658965");
        correctData.put(MEDICINE_FORM_ID, "7452");
        correctData.put(MEDICINE_DOSAGE, "10");
        correctData.put(MEDICINE_DOSAGE_UNIT, "MILLIGRAM");
        correctData.put(MEDICINE_NUMBER_IN_PACKAGE, "20");
        correctData.put(MEDICINE_TOTAL_PACKAGES, "895");
        correctData.put(MEDICINE_PRICE, "14.03");
        correctData.put(MEDICINE_IMAGE_LINK, "D:/images/image.png");
        Assert.assertTrue(dataValidator.isCorrectMedicineData(correctData));
    }

    @Test
    public void testIsCorrectMedicineDataFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(MEDICINE_NAME, "");
        incorrectData.put(MEDICINE_INTERNATIONAL_NAME_ID, "0");
        incorrectData.put(MEDICINE_MANUFACTURER_ID, "0");
        incorrectData.put(MEDICINE_FORM_ID, "0");
        incorrectData.put(MEDICINE_DOSAGE, "10.5");
        incorrectData.put(MEDICINE_DOSAGE_UNIT, "M");
        incorrectData.put(MEDICINE_NUMBER_IN_PACKAGE, "0");
        incorrectData.put(MEDICINE_TOTAL_PACKAGES, "-2");
        incorrectData.put(MEDICINE_PRICE, "14.3");
        incorrectData.put(MEDICINE_IMAGE_LINK, "");
        Assert.assertFalse(dataValidator.isCorrectMedicineData(incorrectData));
    }

    @Test
    public void testIsCorrectChangePasswordData() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(OLD_PASSWORD,"Customer56*");
        correctData.put(NEW_PASSWORD,"Ma12563!");
        correctData.put(REPEAT_PASSWORD,"Ma12563!");
        Assert.assertTrue(dataValidator.isCorrectChangePasswordData(correctData));
    }

    @Test
    public void testIsCorrectChangePasswordDataFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(OLD_PASSWORD,"Customer56");
        incorrectData.put(NEW_PASSWORD,"Ma12563");
        incorrectData.put(REPEAT_PASSWORD,"a12563!");
        Assert.assertFalse(dataValidator.isCorrectChangePasswordData(incorrectData));
    }

    @Test
    public void testIsCorrectQuantity() {
        String correct = "36";
        Assert.assertTrue(dataValidator.isCorrectQuantity(correct));
    }

    @Test
    public void testIsCorrectQuantityFalse() {
        String incorrect = "1000000000";
        Assert.assertFalse(dataValidator.isCorrectQuantity(incorrect));
    }

    @Test
    public void testIsCorrectChangeQuantity() {
        String correct = "-3";
        Assert.assertTrue(dataValidator.isCorrectChangeQuantity(correct));
    }

    @Test
    public void testIsCorrectChangeQuantityFalse() {
        String incorrect = "1000000000";
        Assert.assertFalse(dataValidator.isCorrectChangeQuantity(incorrect));
    }

    @Test
    public void testIsCorrectDosageUnit() {
        String correct = "MILLIGRAM";
        Assert.assertTrue(dataValidator.isCorrectDosageUnit(correct));
    }

    @Test
    public void testIsCorrectDosageUnitFalse() {
        String incorrect = "";
        Assert.assertFalse(dataValidator.isCorrectDosageUnit(incorrect));
    }

    @Test
    public void testIsCorrectPrice() {
        String correct = "14.48";
        Assert.assertTrue(dataValidator.isCorrectPrice(correct));
    }

    @Test
    public void testIsCorrectPriceFalse() {
        String incorrect = "14.485";
        Assert.assertFalse(dataValidator.isCorrectPrice(incorrect));
    }

    @Test
    public void testIsCorrectInternationalName() {
        String correct = "Duphastony";
        Assert.assertTrue(dataValidator.isCorrectInternationalName(correct));
    }

    @Test
    public void testIsCorrectInternationalNameFalse() {
        String incorrect = "";
        Assert.assertFalse(dataValidator.isCorrectInternationalName(incorrect));
    }

    @Test
    public void testIsCorrectManufacturerName() {
        String correct = "BAYER";
        Assert.assertTrue(dataValidator.isCorrectManufacturerName(correct));
    }

    @Test
    public void testIsCorrectManufacturerNameFalse() {
        String incorrect = "";
        Assert.assertFalse(dataValidator.isCorrectManufacturerName(incorrect));
    }

    @Test
    public void testIsCorrectCountry() {
        String correct = "Францыя";
        Assert.assertTrue(dataValidator.isCorrectCountry(correct));
    }

    @Test
    public void testIsCorrectCountryFalse() {
        String incorrect = "";
        Assert.assertFalse(dataValidator.isCorrectCountry(incorrect));
    }

    @Test
    public void testIsCorrectFormName() {
        String correct = "капсулы";
        Assert.assertTrue(dataValidator.isCorrectFormName(correct));
    }

    @Test
    public void testIsCorrectFormNameFalse() {
        String incorrect = "капс&";
        Assert.assertFalse(dataValidator.isCorrectFormName(incorrect));
    }

    @Test
    public void testIsCorrectFormUnit() {
        String correct = "PIECES";
        Assert.assertTrue(dataValidator.isCorrectFormUnit(correct));
    }

    @Test
    public void testIsCorrectFormUnitFalse() {
        String incorrect = "P";
        Assert.assertFalse(dataValidator.isCorrectFormUnit(incorrect));
    }

    @Test
    public void testIsCorrectPrescriptionData() {
        Map<String, String> correctData = new HashMap<>();
        correctData.put(PRESCRIPTION_QUANTITY,"30");
        correctData.put(PRESCRIPTION_DOSAGE,"250");
        correctData.put(PRESCRIPTION_DOSAGE_UNIT,"MICROGRAM");
        correctData.put(PRESCRIPTION_INTERNATIONAL_NAME_ID,"469");
        correctData.put(PRESCRIPTION_UNIT,"TABLES");
        Assert.assertTrue(dataValidator.isCorrectPrescriptionData(correctData));
    }

    @Test
    public void testIsCorrectPrescriptionDataFalse() {
        Map<String, String> incorrectData = new HashMap<>();
        incorrectData.put(PRESCRIPTION_QUANTITY,"30.5");
        incorrectData.put(PRESCRIPTION_DOSAGE,"2500000000000");
        incorrectData.put(PRESCRIPTION_DOSAGE_UNIT,"MICROG");
        incorrectData.put(PRESCRIPTION_INTERNATIONAL_NAME_ID,"-469");
        incorrectData.put(PRESCRIPTION_UNIT,"UNIT");
        Assert.assertFalse(dataValidator.isCorrectPrescriptionData(incorrectData));
    }
}