package com.app.services;

import com.app.model.Person;
import com.app.model.PersonType;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private String externalFilePath = "external_employees.xml";
    private String internalFilePath = "internal_employees.xml";

    public Person find(PersonType type, String firstName, String lastName, String mobile) {
        var people = getAllPeople(type);
        for (var person : people) {
            if (person.getType() == type &&
                    person.getFirstName().equals(firstName) &&
                    person.getLastName().equals(lastName) &&
                    person.getMobile().equals(mobile)) {
                return person;
            }
        }
        return null;
    }

    public void create(Person person) {
        var people = getAllPeople(person.getType());
        people.add(person);
        savePeople(people, person.getType());
    }

    public boolean remove(String personId) {
        var externalPeople = getAllPeople(PersonType.EXTERNAL);
        var internalPeople = getAllPeople(PersonType.INTERNAL);

        for (var person : externalPeople) {
            if (person.getPersonId().equals(personId)) {
                externalPeople.remove(person);
                savePeople(externalPeople, PersonType.EXTERNAL);
                return true;
            }
        }

        for (var person : internalPeople) {
            if (person.getPersonId().equals(personId)) {
                internalPeople.remove(person);
                savePeople(internalPeople, PersonType.INTERNAL);
                return true;
            }
        }
        return false;
    }

    public void modify(Person person) {
        remove(person.getPersonId());
        create(person);
    }

    private List<Person> getAllPeople(PersonType type) {
        var filePath = (type == PersonType.EXTERNAL) ? externalFilePath : internalFilePath;
        List<Person> people = new ArrayList<>();
        try {
            var file = new File(filePath);
            var dbFactory = DocumentBuilderFactory.newInstance();
            var dBuilder = dbFactory.newDocumentBuilder();
            Document document;
            if (file.exists()) {
                document = dBuilder.parse(file);
                document.getDocumentElement().normalize();
                var nodeList = document.getElementsByTagName("person");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    var node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        var element = (Element) node;
                        var personId = element.getAttribute("personId");
                        var firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                        var lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                        var mobile = element.getElementsByTagName("mobile").item(0).getTextContent();
                        var email = element.getElementsByTagName("email").item(0).getTextContent();
                        var pesel = element.getElementsByTagName("pesel").item(0).getTextContent();
                        people.add(new Person(personId, firstName, lastName, mobile, email, pesel, type));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return people;
    }

    private void savePeople(List<Person> people, PersonType type) {
        String filePath = (type == PersonType.EXTERNAL) ? externalFilePath : internalFilePath;
        try {
            var dbFactory = DocumentBuilderFactory.newInstance();
            var dBuilder = dbFactory.newDocumentBuilder();
            Document document;
            Element rootElement;
            if (new File(filePath).exists()) {
                document = dBuilder.parse(filePath);
                rootElement = document.getDocumentElement();
            } else {
                document = dBuilder.newDocument();
                rootElement = document.createElement("root");
                document.appendChild(rootElement);
            }

            for (var person : people) {
                var personElement = document.createElement("person");
                personElement.setAttribute("personId", person.getPersonId());

                var firstNameElement = document.createElement("firstName");
                firstNameElement.appendChild(document.createTextNode(person.getFirstName()));
                personElement.appendChild(firstNameElement);

                var lastNameElement = document.createElement("lastName");
                lastNameElement.appendChild(document.createTextNode(person.getLastName()));
                personElement.appendChild(lastNameElement);

                var mobileElement = document.createElement("mobile");
                mobileElement.appendChild(document.createTextNode(person.getMobile()));
                personElement.appendChild(mobileElement);

                var emailElement = document.createElement("email");
                emailElement.appendChild(document.createTextNode(person.getEmail()));
                personElement.appendChild(emailElement);

                var peselElement = document.createElement("pesel");
                peselElement.appendChild(document.createTextNode(person.getPesel()));
                personElement.appendChild(peselElement);

                rootElement.appendChild(personElement);
            }

            var transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer();
            var source = new DOMSource(document);
            var result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
