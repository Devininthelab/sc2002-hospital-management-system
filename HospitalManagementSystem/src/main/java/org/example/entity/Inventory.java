package org.example.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    private class Medication {
        private final String medicineName;
        private int stockLevel;
        private int lowStockLevel;

        public Medication(String medicineName, int stockLevel, int lowStockLevel) {
            this.medicineName = medicineName;
            this.stockLevel = stockLevel;
            this.lowStockLevel = lowStockLevel;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public int getStockLevel() {
            return stockLevel;
        }

        public int getLowStockLevel() {
            return lowStockLevel;
        }

        public void setStockLevel(int stockLevel) {
            this.stockLevel = stockLevel;
        }

        public void setLowStockLevel(int lowStockLevel) {
            this.lowStockLevel = lowStockLevel;
        }
    }

    // Inventory attributes
    private final List<Medication> medications; // List to store all medications

    // Constructor
    public Inventory() {
        this.medications = new ArrayList<>();
    }

    // Method to add medication to the inventory
    public void addMedicine(String medicineName, int stockLevel, int lowStockLevel) {
        Medication medication = new Medication(medicineName, stockLevel, lowStockLevel);
        medications.add(medication);
        checkLowStockAlert(medication);
    }

    // Method to remove medication from the inventory
    public void removeMedicine(String medicineName) {
        Iterator<Medication> iterator = medications.iterator();
        while (iterator.hasNext()) {
            Medication medication = iterator.next();
            if (medication.getMedicineName().equals(medicineName)) {
                iterator.remove();
                System.out.println("Medication removed successfully.");
                return;
            }
        }
        System.out.println("Medication not found in inventory.");
    }

    // Method to update stock level of a medication
    public void updateStockLevel(String medicineName, int stockLevel) {
        Medication medication = findMedication(medicineName);
        if (medication != null) {
            medication.setStockLevel(stockLevel);
            checkLowStockAlert(medication);
        } else {
            System.out.println("Medication not found.");
        }
    }

    // Method to update the low stock level of a medication (for administrators)
    public void updateLowStockLevel(String medicineName, int lowStockLevel) {
        Medication medication = findMedication(medicineName);
        if (medication != null) {
            medication.setLowStockLevel(lowStockLevel);
            System.out.println("Low stock level updated for " + medicineName);
        } else {
            System.out.println("Medication not found.");
        }
    }

    // Method to check if stock level is below low stock level
    private void checkLowStockAlert(Medication medication) {
        if (medication.getStockLevel() < medication.getLowStockLevel()) {
            System.out.println("Low stock alert for " + medication.getMedicineName());
        }
    }

    // Private helper method to find a medication by name
    private Medication findMedication(String medicineName) {
        for (Medication medication : medications) {
            if (medication.getMedicineName().equals(medicineName)) {
                return medication;
            }
        }
        return null;
    }

    // Method to view all medications in the inventory
    public void viewMedications() {
        for (Medication medication : medications) {
            System.out.println("Medicine Name: " + medication.getMedicineName() +
                    "\nStock Level: " + medication.getStockLevel() +
                    "\nLow Stock Level: " + medication.getLowStockLevel());
        }
    }
}
