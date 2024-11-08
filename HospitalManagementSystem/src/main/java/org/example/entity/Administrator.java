package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends Staff {
    private List<User> staff; // List of hospital staff
    private List<String> appointments; // List of appointment details
    private final Inventory inventory; // Manages inventory of medications

    public Administrator(String username, String password, String contact) {
        super(username, password, "Administrator", contact); // Role is set as "Administrator"
        this.staff = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.inventory = new Inventory();
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

    // View current inventory of medications
    public void viewInventory() {
        inventory.viewInventory();
    }

    // Manage inventory - add medicine
    public void addMedicine(String medicineName, int stockLevel, int lowStockLevel) {
        inventory.addMedicine(medicineName, stockLevel, lowStockLevel);
    }

    // Manage inventory - remove medicine (implemented within Administrator)
    public void removeMedicine(String medicineName) {
        inventory.removeMedicine(medicineName);
    }

    // Manage inventory - update stock level
    public void updateStockLevel(String medicineName, int stockLevel) {
        inventory.updateStockLevel(medicineName, stockLevel);
    }

    // Manage inventory - update low stock level
    public void updateLowStockLevel(String medicineName, int lowStockLevel) {
        inventory.updateLowStockLevel(medicineName, lowStockLevel);
    }

    // Manage inventory - approve replenishment request
    public void approveReplenishmentRequest(String medicineName, int quantity) {
        Medicine medication = inventory.getMedicine(medicineName);
        if (medication != null && medication.isRequested()) {
            int currentStock = medication.getStockLevel();
            inventory.updateStockLevel(medicineName, currentStock + quantity);
            inventory.updateRequest(medicineName, false);
            System.out.println("Replenishment approved for " + medicineName + ". New stock level: " +
                    medication.getStockLevel());
        } else if (medication == null) {
            System.out.println("Medication not found in inventory.");
        } else {
            System.out.println("No pending replenishment request for " + medicineName);
        }
    }
}
