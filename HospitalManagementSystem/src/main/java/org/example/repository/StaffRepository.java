package org.example.repository;

import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.entity.Pharmacist;
import org.example.entity.Staff;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class StaffRepository {
    private List<Staff> staffList;
    private static final String csvPath = "src/main/resources/Staff_List.csv";

    public StaffRepository() {
        staffList = new ArrayList<>();
    }

    private void loadStaffsFromCSV(String csvPath) {
        try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0].trim();
                String name = values[1].trim();
                String role = values[2].trim();
                Patient.Gender gender = Patient.Gender.valueOf(values[4].trim().toUpperCase());
                int age = Integer.parseInt(values[5].trim());
                String password = values[6].trim();

                this.staffList.add(new Staff(id, name, role, gender, age, password));
                // load diagnoses and treament
                // load appointments
                // load appointments outcome records

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number");
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        for (Staff staff : staffList) {
            if (staff.getId().startsWith("D")) {
                Doctor doctor = (Doctor) staff;
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    /**
     * Query the staff list for pharmacist with the matching id
     * @param id
     * @return
     */
    public Pharmacist getPharmacistById(String id) {
        for (Staff staff : staffList) {
            if (staff.getRole().equals("pharmacist") && staff.getId().equals(id)) {
                return (Pharmacist) staff;
            }
        }
        return null;
    }
}
