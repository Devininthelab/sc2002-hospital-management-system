package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    // Inventory attributes
    private final List<Medication> medications;

    // Constructor
    public Inventory() {
        this.medications = new ArrayList<>();
    }
    // helper method to find medicine by name
    private Medication findMedicine(String medicineName) {
        for (Medication medication : medications) {
            if (medication.getName().equals(medicineName)) {
                return medication;
            }
        }
        return null;
    }

    // Public method to access a medication by name
    public Medication getMedicine(String medicineName) {
        return findMedicine(medicineName);
    }

    // View current inventory of medications
    public void viewInventory() {
        System.out.println("Current Inventory:");
        for (Medication medication : medications) {
            System.out.println(medication.toString());
        }
    }

    // create new medicine to inventory
    public void createMedicine(String medicineName, int stockLevel, int lowStockLevel) {
        Medication medication = new Medication(medicineName, stockLevel, lowStockLevel);
        medications.add(medication);
    }

    // update stock level of medicine
    public void updateStockLevel(String medicineName, int newStockLevel) {
        Medication medication = findMedicine(medicineName);
        if (medication != null) {
            medication.setStockLevel(newStockLevel);
        } else {
            System.out.println("Medication not found.");
        }
    }
    // update low stock level of medicine
    public void updateLowStockLevel(String medicineName, int newLowStockLevel) {
        Medication medication = findMedicine(medicineName);
        if (medication != null) {
            medication.setLowStockAlert(newLowStockLevel);
        } else {
            System.out.println("Medication not found.");
        }
    }

    // Update replenishment request status
    public void updateRequest(String medicineName, boolean requested) {
        Medication medication = findMedicine(medicineName);
        if (medication != null) {
            medication.setRequested(requested);
        } else {
            System.out.println("Medication not found.");
        }
    }
}
