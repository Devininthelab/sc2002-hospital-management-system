package org.example.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Pharmacist is a subclass of Staff
 * Pharmacist is a staff that is responsible for managing the stock of medicines in the clinic.
 * TODO: Consider removing this class and use Staff class directly
 */
public class Pharmacist extends Staff {
    public Pharmacist(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);  // Role is set as "Pharmacist"
    }
}
