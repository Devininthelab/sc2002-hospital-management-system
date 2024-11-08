package org.example.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    // Inventory attributes
    private List<Medicine> medicines; // List to store all medications

    // Constructor
    public Inventory() {
        this.medicines = new ArrayList<>();
    }
    public void createMedicine(String medicineName, int stockLevel, int lowStockLevel) {
        Medicine medicine = new Medicine(medicineName, stockLevel, lowStockLevel);
        medicines.add(medicine);
    }
    // Method to add medication to the inventory
    public void addMedicine(String medicineName, int stockLevel, int lowStockLevel) {
        Medicine medicine = new Medicine(medicineName, stockLevel, lowStockLevel);
        medicines.add(medicine);
        checkLowStockAlert(medicine);
    }
    // Method to update replenishment request status
    public void updateRequest(String medicineName, boolean requested) {
        Medicine medicine = findMedicine(medicineName);
        if (medicine != null) {
            medicine.setRequested(requested);
            System.out.println("Request status updated for " + medicineName + " to " + requested);
        } else {
            System.out.println("Medicine not found.");
        }
    }
    // Method to remove medication from the inventory
    public void removeMedicine(String medicineName) {
        Iterator<Medicine> iterator = medicines.iterator();
        while (iterator.hasNext()) {
            Medicine medicine = iterator.next();
            if (medicine.getName().equals(medicineName)) {
                iterator.remove();
                System.out.println("Medicine removed successfully.");
                return;
            }
        }
        System.out.println("Medicine not found in inventory.");
    }

    // Method to update stock level of a medication
    public void updateStockLevel(String medicineName, int stockLevel) {
        Medicine medicine = findMedicine(medicineName);
        if (medicine != null) {
            medicine.setStockLevel(stockLevel);
            checkLowStockAlert(medicine);
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Method to update the low stock level of a medication (for administrators)
    public void updateLowStockLevel(String medicineName, int lowStockLevel) {
        Medicine medicine = findMedicine(medicineName);
        if (medicine != null) {
            medicine.setLowStockAlert(lowStockLevel);
            System.out.println("Low stock level updated for " + medicineName);
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Method to check if stock level is below low stock level
    private void checkLowStockAlert(Medicine medicine) {
        if (medicine.getStockLevel() < medicine.getLowStockAlert()) {
            System.out.println("Low stock alert for " + medicine.getName());
        }
    }

    // Private helper method to find a medication by name
    private Medicine findMedicine(String medicineName) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equals(medicineName)) {
                return medicine;
            }
        }
        return null;
    }

    // Method to view all medications in the inventory
    public void viewMedicines() {
        for (Medicine medicine : medicines) {
            System.out.println("Medicine Name: " + medicine.getName() +
                    "\nStock Level: " + medicine.getStockLevel() +
                    "\nLow Stock Level: " + medicine.getLowStockAlert());
        }
    }
}
