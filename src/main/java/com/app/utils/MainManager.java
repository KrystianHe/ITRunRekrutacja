package com.app.utils;

import com.app.model.Person;
import com.app.model.PersonType;
import com.app.services.PersonService;

public class MainManager {

    public void showDetails() {
        var database = new PersonService();

        // New person
        var person = new Person(
                "1",
                "Jan",
                "Kowalski",
                "+48123456780",
                "example@gmail.com",
                "80010112345",
                PersonType.INTERNAL
        );

        // Adding new person
        database.create(person);

        // Search person in database
        var foundPerson = database.find(PersonType.INTERNAL, "Jan", "Kowalski", "+48123456780");

        // Verify added person
        if (foundPerson != null) {
            System.out.println("Good job!:");
            System.out.println("TYPE: " + foundPerson.getType());
            System.out.println("ID: " + foundPerson.getPersonId());
            System.out.println("NAME: " + foundPerson.getFirstName());
            System.out.println("LASTNAME: " + foundPerson.getLastName());
            System.out.println("MOBILE: " + foundPerson.getMobile());
            System.out.println("EMAIL: " + foundPerson.getEmail());
            System.out.println("PESEL: " + foundPerson.getPesel());
            System.out.println("TYP: " + foundPerson.getPesel());

            // Modify person data
            foundPerson.setType(PersonType.EXTERNAL);
            foundPerson.setFirstName("Anna");
            foundPerson.setLastName("Nowak");
            foundPerson.setMobile("+48123456789");
            foundPerson.setEmail("anna@gmail.com");
            foundPerson.setPesel("80010112345");

            // Modify method
            database.modify(foundPerson);

            System.out.println("\nAfter modify:");
            System.out.println("TYPE: " + foundPerson.getType());
            System.out.println("ID: " + foundPerson.getPersonId());
            System.out.println("NAME: " + foundPerson.getFirstName());
            System.out.println("LASTNAME: " + foundPerson.getLastName());
            System.out.println("MOBILE: " + foundPerson.getMobile());
            System.out.println("EMAIL: " + foundPerson.getEmail());
            System.out.println("PESEL: " + foundPerson.getPesel());
            System.out.println("TYP: " + foundPerson.getPesel());

            // Delete person
            boolean removed = database.remove(foundPerson.getPersonId());
            if (removed) {
                System.out.println("\nDeleted person.");
            }
        } else {
            System.out.println("Person not found.");
        }
    }
}
