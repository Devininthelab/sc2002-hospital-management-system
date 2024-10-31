package org.example.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Administrator extends User {
    private List<User> staff; // List of hospital staff
    private List<String> appointments; // List of appointment details
    private Map<String, Integer> inventory; // Maps medication name to stock level

    public Administrator(String username, String password, String contact) {
        super(username, password, "Administrator", contact); // Role is set as "Administrator"
        this.staff = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.inventory = new HashMap<>();
    }

    // Add a new staff member
    public void addStaff(User newStaff) {
        staff.add(newStaff);
        System.out.println("Staff member " + newStaff.getUsername() + " added successfully.");
    }

    // Update existing staff details
    public void updateStaff(int staffId, String newUsername, String newContact) {
        for (User member : staff) {
            if (member.getId() == staffId) {
                member.updateProfile(newUsername, newContact);
                System.out.println("Staff member " + staffId + " updated successfully.");
                return;
            }
        }
        System.out.println("Staff member with ID " + staffId + " not found.");
    }

    // Remove a staff member
    public void removeStaff(int staffId) {
        staff.removeIf(member -> member.getId() == staffId);
        System.out.println("Staff member with ID " + staffId + " removed successfully.");
    }

    // View appointments
    public void viewAppointments() {
        System.out.println("Appointments:");
        for (String appointment : appointments) {
            System.out.println(appointment);
        }
    }

    // Manage inventory: add or update stock level of a medication
    public void manageInventory(String medication, int stockLevel) {
        inventory.put(medication, stockLevel);
        System.out.println("Inventory updated: " + medication + " stock level set to " + stockLevel);
    }

    // Approve a replenishment request
    public void approveReplenishment(String medication, int quantity) {
        int currentStock = inventory.getOrDefault(medication, 0);
        inventory.put(medication, currentStock + quantity);
        System.out.println("Replenishment approved for " + medication + ". New stock level: " + (currentStock + quantity));
    }
}