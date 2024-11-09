package org.example.repository;

import org.example.entity.Medication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicationRepository {
    private List<Medication> medications = new ArrayList<>();
    private final String filePath = "src/main/resources/Medications.csv";

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
                    Medication.Status status = Medication.Status.valueOf(values[3].trim().toUpperCase());

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

    public List<Medication> getMedicationsById(int prescriptionId) {
        List<Medication> result = new ArrayList<>();
        for (Medication medication : medications) {
            if (medication.getId() == prescriptionId) {
                result.add(medication);
            }
        }
        return result;
    }

    // Additional method to add a new medication and save to CSV
    public void addMedication(Medication medication) {
        medications.add(medication);
        saveMedicationsToCSV(); // Save changes after adding
    }

    public void addMedication(int id, String name, int quantity) {
        Medication medication = new Medication(id, name, quantity);
        medications.add(medication);
        saveMedicationsToCSV();
    }
}