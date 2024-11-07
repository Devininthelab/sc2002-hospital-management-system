package org.example.entity;

import java.util.HashMap;
import java.util.Map;

public class Pharmacist extends Staff {
    private Map<String, String> prescriptions; // Maps prescription ID to its status
    private final Inventory inventory;   // Maps Medicine_List.csv name to stock level
    public Pharmacist(String username, String password, String contact) {
        super(username, password, "Pharmacist", contact);  // Role is set as "Pharmacist"
        this.prescriptions = new HashMap<>();
        this.inventory = new Inventory();
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
    public void viewInventory(){
        inventory.viewInventory();
    }

    // Submit a replenishment request for low-stock medications
    public void submitReplenishmentRequest(String medicineName) {
        Medication medication = inventory.getMedicine(medicineName);
        if (medication != null) {
            if (medication.getStockLevel() < medication.getLowStockAlert()) {
                medication.setRequested(true);
                System.out.println("Replenishment request for " + medicineName + " submitted.");
            } else {
                System.out.println("Stock level for " + medicineName + " is above low-stock alert.");
            }
        } else {
            System.out.println("Medication not found.");
        }
    }
    
}
