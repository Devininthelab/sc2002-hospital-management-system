package org.example.repository;

import org.example.entity.Prescription;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository {
    private List<Prescription> prescriptions;
    private String filePath = "src/main/resources/Prescription.csv";

    public PrescriptionRepository() {
        this.prescriptions = new ArrayList<>();
        loadPrescriptionsFromCSV();
    }

    /**
     * Load Prescriptions from CSV file
     * Parse Prescription data from CSV file and store in Prescriptions list
     */
    public void loadPrescriptionsFromCSV() {
        String line;
        prescriptions.clear(); // Clear list before loading to avoid duplicates

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int medId = Integer.parseInt(values[0].trim());
                    String name = values[1].trim();
                    int quantity = Integer.parseInt(values[2].trim());
                    String status = values[3].trim().toUpperCase();
                    Prescription prescription = new Prescription(medId, name, quantity, status);
                    prescriptions.add(prescription);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing Prescription data: " + e.getMessage());
        }
    }

    /**
     * Overwrite the CSV file with the most up-to-date list of Prescriptions
     */
    public void savePrescriptionsToCSV(List<Prescription> prescriptions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Prescription prescription : prescriptions) {
                bw.write(prescription.getId() + "," + prescription.getName() + "," +
                        prescription.getQuantity() + "," + prescription.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public List<Prescription> getPrescriptionsById(int prescriptionId) {
        List<Prescription> result = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == prescriptionId) {
                result.add(prescription);
            }
        }
        return result;
    }

    // Additional method to add a new Prescription and save to CSV
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        savePrescriptionsToCSV(); // Save changes after adding
    }

    /**
     * Read Prescriptions by name and id
     * @return List of Prescriptions
     */
    public Prescription getPrescriptionsByNameAndId(int prescriptionId, String name) {
        for (Prescription Prescription : prescriptions) {
            if (Prescription.getId() == prescriptionId && Prescription.getName().equalsIgnoreCase(name)) {
                // TODO: consider not return the object itself, but a copy of it
                return Prescription;
            }
        }
        return null;
    }

    /**
     * Create operations on Prescription
     * @param id
     * @param name
     * @param quantity
     */
    public void addPrescription(int id, String name, int quantity) {
        Prescription prescription = new Prescription(id, name, quantity);
        prescriptions.add(prescription);
        savePrescriptionsToCSV();
    }

    /**
     * Update operations on Prescription status
     * @param id is the same as appointment id and appointment outcome record id
     * @param status can be PENDING, DISPENSED
     */
    public void updatePrescriptionStatus(int id, String PrescriptionName, String status) {
        Prescription Prescription = getPrescriptionsByNameAndId(id, PrescriptionName);
        if (Prescription != null) {
            Prescription.setStatus(status.toUpperCase());
        }
    }

    public boolean isValidPrescription(int appointmentId, String presciptionName) {
        return prescriptions.stream()
                .anyMatch(prescription -> prescription.getId() == appointmentId
                        && prescription.getName().equalsIgnoreCase(presciptionName));
    }
}
