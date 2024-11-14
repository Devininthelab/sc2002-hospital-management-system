package org.example.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Pharmacist is a subclass of Staff
 * Pharmacist is a staff that is responsible for managing the stock of medicines in the clinic.
 * TODO: Consider removing this class and use Staff class directly
 */
public class Pharmacist extends Staff {
    public Pharmacist(String id, String password, String gender, String name, int age) {
        super(id, name, "Pharmacist", gender, age, password);  // Role is set as "Pharmacist"
    }
}
