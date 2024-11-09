package org.example.repository;

import org.example.entity.Medication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicationRepository {
    private List<Medication> medications = new ArrayList<>();
    private final String filePath = "src/main/resources/Medications.csv";

    public void loadMedicationsFromCSV() {
        String line;

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

    public void updateMedicationStatus(int id, String medicationName, Medication.Status status) {

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
}