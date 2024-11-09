package org.example.entity;

import org.example.repository.AppointmentRepository;
import org.example.repository.MedicineRepository;
import org.example.repository.StaffRepository;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends Staff {
    public Administrator(String id, String name, String gender, int age, String password){
        super(id, name, "Administrator", gender, age, password);
    }


}
