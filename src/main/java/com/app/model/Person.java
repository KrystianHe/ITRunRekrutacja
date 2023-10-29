package com.app.model;

import com.app.utils.DataValidator;
import lombok.Data;

@Data
public class Person {

    private String personId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String pesel;
    private PersonType type;

    public Person(String personId, String firstName, String lastName, String mobile, String email, String pesel, PersonType type) {
        DataValidator validator = new DataValidator();

        if (validator.isValidName(firstName)
                && validator.isValidName(lastName)
                && validator.isValidPhoneNumber(mobile)
                && validator.isValidEmail(email)
                && validator.isValidPesel(pesel)) {
            this.personId = personId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.mobile = mobile;
            this.email = email;
            this.pesel = pesel;
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid data for Person");
        }
    }
}
