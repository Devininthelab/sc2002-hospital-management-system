package org.example.repository;

import org.example.entity.Medicine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is also can be know as InventoryRepository. This class is responsible for managing the stock of medicines in the clinic.
 */
public class MedicineRepository {
    private List<Medicine> medicines;
    private final String filePath = "src/main/resources/Medicine_List.csv";

    public MedicineRepository() {
        medicines = new ArrayList<>();
    }

    public void loadMedicinesFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String name = values[0].trim();
                    int stockLevel = Integer.parseInt(values[1].trim());
                    int lowStockAlert = Integer.parseInt(values[2].trim());
                    Medicine medication = new Medicine(name, stockLevel, lowStockAlert);
                    medicines.add(medication);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
    public void saveMedicineToCSV(Medicine medicine, String filePath) {
        //IMPLEMENT LATER
    }
    public void addMedicine(Medicine medicine) {
        medicineMap.put(medicine.getName(), medicine);
        saveMedicineToCSV(medicine, filePath);
    }
    public Medicine getMedication(String name) {
        return medicineMap.get(name);
    }

    public void updateStockLevel(String name, int newStockLevel) {
        Medicine medicine = medicineMap.get(name);
        if (medicine != null) {
            medicine.setStockLevel(newStockLevel);
        }
    }
    public void updateLowStockAlert(String name, int newLowStockAlert) {
        Medicine medicine = medicineMap.get(name);
        if (medicine != null) {
            medicine.setLowStockAlert(newLowStockAlert);
        }
    }

    public void displayMedications() {
        medicineMap.values().forEach(System.out::println);
    }
}
