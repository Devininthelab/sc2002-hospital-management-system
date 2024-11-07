package org.example.repository;

import org.example.entity.Medication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MedicationRepository {
    private Map<String, Medication> medicationMap = new HashMap<>();

    public void loadMedications(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String name = values[0].trim();
                    int stockLevel = Integer.parseInt(values[1].trim());
                    int lowStockAlert = Integer.parseInt(values[2].trim());
                    Medication medication = new Medication(name, stockLevel, lowStockAlert);
                    medicationMap.put(name, medication);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public Medication getMedication(String name) {
        return medicationMap.get(name);
    }

    public void updateStockLevel(String name, int newStockLevel) {
        Medication medication = medicationMap.get(name);
        if (medication != null) {
            medication.setStockLevel(newStockLevel);
        }
    }
    public void updateLowStockAlert(String name, int newLowStockAlert) {
        Medication medication = medicationMap.get(name);
        if (medication != null) {
            medication.setLowStockAlert(newLowStockAlert);
        }
    }

    public void displayMedications() {
        medicationMap.values().forEach(System.out::println);
    }
}
