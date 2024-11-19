package org.example.entity;

import org.example.repository.AppointmentRepository;
import org.example.repository.MedicineRepository;
import org.example.repository.StaffRepository;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends Staff {

    /**
     * Constructor for Administrator
     * @param id the id of the administrator
     * @param name the name of the administrator
     * @param role the role of the administrator
     * @param gender the gender of the administrator
     * @param age the age of the administrator
     * @param password the password of the administrator
     */
    public Administrator(String id, String name, String role, String gender, int age, String password){
        super(id, name, "Administrator", gender, age, password);
    }


}
