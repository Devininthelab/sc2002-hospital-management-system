package org.example.repository;

import org.example.entity.Staff;
import org.example.entity.Staff.Gender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaffRepository {
    private List<Staff> staffs = new ArrayList<>();
    private static final String staffDataset = "src/main/resources/Staff_List.csv";

    public StaffRepository() {
        // Load staff data from the CSV file by default
        loadStaffsFromCSV(staffDataset);
    }

    public void loadStaffsFromCSV(String csvPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean isFirstLine = true; // Skip header
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { // Skip the header row
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                String staffId = values[0].trim();
                String name = values[1].trim();
                String role = values[2].trim();

                // Parse gender as enum
                Gender gender;
                try {
                    gender = Gender.valueOf(values[3].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid gender value for staff ID " + staffId + ", setting to OTHER.");
                    gender = Gender.OTHER;
                }

                int age = Integer.parseInt(values[4].trim());

                // Create Staff object and add it to the list, using a placeholder password
                Staff staff = new Staff(staffId, "defaultPassword", role, name, age, gender);
                this.staffs.add(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing age or other numeric fields");
        }
    }

    public List<Staff> getStaffs() {
        return staffs;
    }
}