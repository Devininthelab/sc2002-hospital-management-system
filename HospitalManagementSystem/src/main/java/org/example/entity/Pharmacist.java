package org.example.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Pharmacist is a subclass of Staff.
 * <p>
 * A pharmacist is responsible for managing the stock of medicines in the clinic.
 * This includes tasks like inventory management and ensuring medicine availability.
 * </p>
 */
public class Pharmacist extends Staff {

    /**
     * Constructor to initialize a Pharmacist object.
     *
     * @param id       the unique identifier for the pharmacist
     * @param name     the name of the pharmacist
     * @param role     the role of the pharmacist (e.g., "Pharmacist")
     * @param gender   the gender of the pharmacist
     * @param age      the age of the pharmacist
     * @param password the password for the pharmacist's account
     */
    public Pharmacist(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);  // Role is set as "Pharmacist"
    }
}
