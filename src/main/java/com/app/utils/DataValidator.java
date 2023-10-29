package com.app.utils;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;


public class DataValidator {

    /**
     * Validate email
     * @param email
     * @return Correct email
     */

    public boolean isValidEmail(String email){
        var emailAddress = EmailValidator.getInstance();
        return emailAddress.isValid(email);
    }

    /**
     * Validate pesel
     * @return Correct pesel
     */
    public boolean isValidPesel(String pesel) {
        var peselRegex = "\\d{11}";
        return Pattern.matches(peselRegex, pesel);
    }

    /**
     * Validate name, lastname
     * @param name
     * @return Correct values for name, lastname
     */

    public boolean isValidName(String name) {
        var firstNameRegex = "^[a-zA-Z ]{2,50}$";
        return Pattern.matches(firstNameRegex, name);
    }

    /**
     * Validate phone number
     * @param phone
     * @return Correct number
     */
    public boolean isValidPhoneNumber(String phone) {
        var phonePattern = "\\+\\d{2}\\d{9}";
        return phone.matches(phonePattern);
    }
}
