package org.example.entity;

import org.example.utils.Gender;

import java.util.HashMap;
import java.util.Map;

public class Pharmacist extends Staff {
    public Pharmacist(String id, String password, Gender gender, String name, int age) {
        super(id, name, "Pharmacist", gender, age, password);  // Role is set as "Pharmacist"
        //this.prescriptions = new HashMap<>();
        //this.inventory = new Inventory();
    }

    //// View appointment outcome record for a specific prescription
    //public String viewPrescriptionStatus(String prescriptionId) {
    //    return prescriptions.getOrDefault(prescriptionId, "Prescription not found.");
    //}

    //// Update the status of a prescription
    //public void fullfillPrescriptionStatus(String prescriptionId, String status) {
    //    if (prescriptions.containsKey(prescriptionId)) {
    //        prescriptions.put(prescriptionId, status);
    //        System.out.println("Prescription status updated to: " + status);
    //    } else {
    //        System.out.println("Prescription not found.");
    //    }
    //}

    //// Submit a replenishment request for low-stock medications
    //public void submitReplenishmentRequest(String medicineName) {
    //    Medication medication = inventory.getMedicine(medicineName);
    //    if (medication != null) {
    //        if (medication.getStockLevel() < medication.getLowStockAlert()) {
    //            medication.setRequested(true);
    //            System.out.println("Replenishment request for " + medicineName + " submitted.");
    //        } else {
    //            System.out.println("Stock level for " + medicineName + " is above low-stock alert.");
    //        }
    //    } else {
    //        System.out.println("Medication not found.");
    //    }
    //}
    
}
