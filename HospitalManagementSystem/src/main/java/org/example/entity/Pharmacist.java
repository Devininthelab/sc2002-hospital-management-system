package org.example.entity;

import java.util.HashMap;
import java.util.Map;

public class Pharmacist extends User {
    private Map<String, String> prescriptions; // Maps prescription ID to its status
    private Map<String, Integer> inventory;    // Maps medication name to stock level

    public Pharmacist(String username, String password, String contact) {
        super(username, password, "Pharmacist", contact);  // Role is set as "Pharmacist"
        this.prescriptions = new HashMap<>();
        this.inventory = new HashMap<>();
    }

    // View appointment outcome record for a specific prescription
    public String viewPrescriptionStatus(String prescriptionId) {
        return prescriptions.getOrDefault(prescriptionId, "Prescription not found.");
    }

    // Update the status of a prescription
    public void fullfillPrescriptionStatus(String prescriptionId, String status) {
        if (prescriptions.containsKey(prescriptionId)) {
            prescriptions.put(prescriptionId, status);
            System.out.println("Prescription status updated to: " + status);
        } else {
            System.out.println("Prescription not found.");
        }
    }

    // View current inventory of medications
    public void viewInventory() {
        System.out.println("Medication Inventory:");
        inventory.forEach((medication, stockLevel) ->
                System.out.println("Medication: " + medication + ", Stock Level: " + stockLevel)
        );
    }

    // Submit a replenishment request for low-stock medications
    public void submitReplenishmentRequest(String medication, int quantity) {
        int currentStock = inventory.getOrDefault(medication, 0);
        inventory.put(medication, currentStock + quantity);
        System.out.println("Replenishment request submitted for " + medication + ", new stock level: " + (currentStock + quantity));
    }
}
