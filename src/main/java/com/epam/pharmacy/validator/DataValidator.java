package com.epam.pharmacy.validator;

import java.util.Map;

/**
 * The interface DataValidator.
 */
public interface DataValidator {
    /**
     * Validate the user login.
     *
     * @param login the user login.
     * @return {@code true}, if the user login is valid.
     */
    boolean isCorrectLogin(String login);

    /**
     * Validate the user password.
     *
     * @param password the user password.
     * @return {@code true}, if the user password is valid.
     */
    boolean isCorrectPassword(String password);

    /**
     * Validate the username.
     *
     * @param name the user name.
     * @return {@code true}, if the username is valid.
     */
    boolean isCorrectUserName(String name);

    /**
     * Validate the user birthday date.
     *
     * @param birthdayDate the user birthday date.
     * @return {@code true}, if the user birthday date is valid.
     */
    boolean isCorrectBirthdayDate(String birthdayDate);

    /**
     * Validate the user sex.
     *
     * @param sex the user sex.
     * @return {@code true}, if the user sex is valid.
     */
    boolean isCorrectSex(String sex);

    /**
     * Validate the phone.
     *
     * @param phone the phone number.
     * @return {@code true}, if the phone number is valid.
     */
    boolean isCorrectPhone(String phone);

    /**
     * Validate the string.
     *
     * @param string the string.
     * @return {@code true}, if the string is not null and not empty.
     */
    boolean isNotEmpty(String string);

    /**
     * Validate the user role.
     *
     * @param userRole the user role.
     * @return {@code true}, if the user role is valid.
     */
    boolean isCorrectUserRole(String userRole);

    /**
     * Validate the language name.
     *
     * @param language the language name.
     * @return {@code true}, if the language name is valid.
     */
    boolean isCorrectLanguage(String language);

    /**
     * Validate the user register data.
     *
     * @param userData the user register data.
     * @return {@code true}, if the user register data is valid.
     */
    boolean isCorrectRegisterData(Map<String, String> userData);

    /**
     * Validate the address.
     *
     * @param address the address.
     * @return {@code true}, if the address is valid.
     */
    boolean isCorrectAddress(String address);

    /**
     * Validate the id.
     *
     * @param id the id.
     * @return {@code true}, if the id is valid.
     */
    boolean isCorrectId(String id);

    /**
     * Validate the user state.
     *
     * @param state the user state.
     * @return {@code true}, if the user state is valid.
     */
    boolean isCorrectUserState(String state);

    /**
     * Validate the medicine data.
     *
     * @param medicineData the medicine data.
     * @return {@code true}, if the medicine data is valid.
     */
    boolean isCorrectMedicineData(Map<String, String> medicineData);

    /**
     * Validate the quantity.
     *
     * @param value the quantity.
     * @return {@code true}, if the quantity is valid.
     */
    boolean isCorrectQuantity(String value);

    /**
     * Validate the quantity to change.
     *
     * @param value the quantity to change.
     * @return {@code true}, if the quantity to change is valid.
     */
    boolean isCorrectChangeQuantity(String value);

    /**
     * Validate the dosage unit.
     *
     * @param unit the dosage unit.
     * @return {@code true}, if the dosage unit is valid.
     */
    boolean isCorrectDosageUnit(String unit);

    /**
     * Validate the price.
     *
     * @param price the price.
     * @return {@code true}, if the price is valid.
     */
    boolean isCorrectPrice(String price);

    /**
     * Validate the medicine international name.
     *
     * @param name the medicine international name.
     * @return {@code true}, if the medicine international name is valid.
     */
    boolean isCorrectInternationalName(String name);

    /**
     * Validate the manufacturer name.
     *
     * @param name the manufacturer name.
     * @return {@code true}, if the manufacturer name is valid.
     */
    boolean isCorrectManufacturerName(String name);

    /**
     * Validate the country name.
     *
     * @param country the country name.
     * @return {@code true}, if the country name is valid.
     */
    boolean isCorrectCountry(String country);

    /**
     * Validate the medicine form name.
     *
     * @param name the medicine form name.
     * @return {@code true}, if the medicine form name is valid.
     */
    boolean isCorrectFormName(String name);

    /**
     * Validate the form unit.
     *
     * @param unit the form unit.
     * @return {@code true}, if the form unit is valid.
     */
    boolean isCorrectFormUnit(String unit);

    /**
     * Validate the prescription data.
     *
     * @param data the prescription data.
     * @return {@code true}, if the prescription data is valid.
     */
    boolean isCorrectPrescriptionData(Map<String, String> data);

    /**
     * Validate the user personal data.
     *
     * @param userData the user personal data.
     * @return {@code true}, if the user personal data is valid.
     */
    boolean isCorrectUserPersonalData(Map<String, String> userData);

    /**
     * Validate the data for search the user.
     *
     * @param paramsMap the data for search the user.
     * @return {@code true}, if the data for search the user is valid.
     */
    boolean isCorrectUserSearchParamsMap(Map<String, String> paramsMap);

    /**
     * Validate the data for search the medicine.
     *
     * @param paramsMap the data for search the medicine.
     * @return {@code true}, if the data for search the medicine is valid.
     */
    boolean isCorrectMedicineSearchParamsMap(Map<String, String> paramsMap);

    /**
     * Validate the value for deposit to the account.
     *
     * @param value the value for deposit to the account.
     * @return {@code true}, if the value for deposit to the account is valid.
     */
    boolean isCorrectDepositValue(String value);

    /**
     * Validate the data for changing the password.
     *
     * @param passwordData the data for changing the password.
     * @return {@code true}, if the data for changing the password is valid.
     */
    boolean isCorrectChangePasswordData(Map<String, String> passwordData);
}
