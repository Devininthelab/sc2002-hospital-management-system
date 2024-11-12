package org.example.repository;

import org.example.entity.Administrator;
import org.example.entity.Doctor;
import org.example.entity.Pharmacist;
import org.example.entity.Staff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class StaffRepository {
    private List<Staff> staffList;
    private static final String csvPath = "src/main/resources/Staff_List.csv";

    public StaffRepository() {
        staffList = loadStaffsFromCSV();
    }

    /**
     * Load staff data from CSV file, then return a list of staff members
     *
     * @return List of staff members
     */
    private List<Staff> loadStaffsFromCSV() {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0].trim();
                String name = values[1].trim();
                String role = values[2].trim();
                String gender = values[3].trim();
                int age = Integer.parseInt(values[4].trim());
                String password = values[5].trim();
                staffList.add(new Staff(id, name, role, gender, age, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number");
        }
        return staffList;
    }


    /**
     * Save staff data to the CSV file
     * This function will be called everytime there is a change in the staff list (CREATE, UPDATE, DELETE)
     */
    public void saveStaffsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
            for (Staff staff : staffList) {
                bw.write(staff.getId() + "," + staff.getName() + "," + staff.getRole() + "," + staff.getGender() + "," + staff.getAge() + "," + staff.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if whether the staff has existed in the staff list
     *
     * @param id The staff id
     * @return true if the staff id exists, false otherwise
     */
    private boolean isStaffIdExist(String id) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Add a staff into the CSV file
     *
     * @param staff The staff to be added
     */
    public void addStaffRepo(Staff staff) {
        if (!isStaffIdExist(staff.getId())) {
            staffList.add(staff);
            saveStaffsToCSV();
            System.out.println("Staff member " + staff.getName() + " added successfully.");
        } else {
            System.out.println("Staff member with ID " + staff.getId() + " already exists.");
        }
    }

    /**
     * Update the staff details in the CSV file
     *
     * @param staffId  The staff id to be updated
     * @param field    Fileld to be updated
     * @param newValue The new value to be updated
     */
    public void updateStaffRepo(String staffId, String field, String newValue) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(staffId)) {
                switch (field) {
                    case "name":
                        staff.setName(newValue);
                        break;
                    case "role":
                        staff.setRole(newValue);
                        break;
                    case "password":
                        staff.setPassword(newValue);
                        break;
                    case "gender":
                        staff.setGender(newValue);
                        break;
                    case "age":
                        staff.setAge(Integer.parseInt(newValue));
                        break;
                }
                saveStaffsToCSV();
            }
        }
        System.out.println("Staff member with ID " + staffId + " not found.");
    }

    /**
     * Remove a staff from the CSV file
     *
     * @param staffId The staff id to be removed
     */
    public void removeStaffRepo(String staffId) {
        staffList.removeIf(staff -> staff.getId().equals(staffId));
        saveStaffsToCSV();
        System.out.println("Staff member with ID " + staffId + " removed successfully.");
    }


    /**
     * Get Administrator by credentials
     *
     * @return a specific administrator if credentials are correct, null otherwise
     */
    public Administrator getAdministratorByCredentials(String id, String password) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id) && staff.getPassword().equals(password)) {
                return (Administrator) staff;
            }
        }
        return null;
    }


    /**
     * Get Doctor by credentials
     *
     * @return a specific doctor if credentials are correct, null otherwise
     */
    public Doctor getDoctorByCredentials(String id, String password) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id) && staff.getPassword().equals(password)) {
                return (Doctor) staff;
            }
        }
        return null;
    }

    /**
     * Get Pharmacist by credentials
     *
     * @return a specific pharmacist if credentials are correct, null otherwise
     */
    public Pharmacist getPharmacistByCredentials(String id, String password) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id) && staff.getPassword().equals(password)) {
                return (Pharmacist) staff;
            }
        }
        return null;
    }

    /**
     * View the list of staff members
     * Current format is like this:
     * List of staff members:
     * ID: 1,   Name: John Doe,     Role: Doctor, ...
     * ....
     */
    public void viewStaffListRepo() {
        System.out.println("List of staff members:");
        for (Staff staff : staffList) {
            System.out.println(staff.toString());
        }
    }


    public Pharmacist getPharmacistById(String id) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id) && staff.getRole().equals("Pharmacist")) {
                return (Pharmacist) staff;
            }
        }
        return null;
    }
}