package org.example.repository;

import org.example.entity.Medication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing the stock of medications in the hospital.
 * Dependencies injected:
 * - filePath: Path to the CSV file storing medication data
 */
public class MedicationRepository {
    private List<Medication> medications;
    private String filePath = "src/main/resources/Medications.csv";

    /**
     * Constructor to inject dependencies
     * @param filePath
     */
    public MedicationRepository(String filePath) {
        medications = new ArrayList<>();
        this.filePath = filePath;
        loadMedicationsFromCSV();
    }

    /**
     * Load medications from CSV file
     * Parse medication data from CSV file and store in medications list
     */
    public void loadMedicationsFromCSV() {
        String line;
        medications.clear(); // Clear list before loading to avoid duplicates

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int medId = Integer.parseInt(values[0].trim());
                    String name = values[1].trim();
                    int quantity = Integer.parseInt(values[2].trim());
                    String status = values[3].trim().toUpperCase();

                    Medication medication = new Medication(medId, name, quantity, status);
                    medications.add(medication);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing medication data: " + e.getMessage());
        }
    }

    /**
     * Overwrite the CSV file with the most up-to-date list of medications
     */
    public void saveMedicationsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Medication medication : medications) {
                bw.write(medication.getId() + "," + medication.getName() + "," +
                        medication.getQuantity() + "," + medication.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Read all medications
     * @return List of medications
     */
    public List<Medication> getMedicationsById(int prescriptionId) {
        List<Medication> result = new ArrayList<>();
        for (Medication medication : medications) {
            if (medication.getId() == prescriptionId) {
                result.add(medication);
            }
        }
        return result;
    }

    /**
     * Read medications by name and id
     * @return List of medications
     */
    public Medication getMedicationsByNameAndId(int prescriptionId, String name) {
        for (Medication medication : medications) {
            if (medication.getId() == prescriptionId && medication.getName().equalsIgnoreCase(name)) {
                // TODO: consider not return the object itself, but a copy of it
                return medication;
            }
        }
        return null;
    }

    /**
     * Create operations on medication
     * @param id
     * @param name
     * @param quantity
     */
    public void addMedication(int id, String name, int quantity) {
        Medication medication = new Medication(id, name, quantity, "PENDING");
        medications.add(medication);
        saveMedicationsToCSV();
    }

    /**
     * Update operations on medication status
     * @param id
     * @param status
     */
    public void updateMedicationStatus(int id, String medicationName, String status) {
        Medication medication = getMedicationsByNameAndId(id, medicationName);
        if (medication != null) {
            medication.setStatus(status.toUpperCase());
        }
    }

}
