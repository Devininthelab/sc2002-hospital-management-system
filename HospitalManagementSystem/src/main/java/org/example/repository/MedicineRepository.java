package org.example.repository;

import org.example.entity.Medicine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This can also can be interpreted as InventoryRepository.
 * This class is responsible for managing the stock of medicines in the clinic.
 */
public class MedicineRepository {
    private List<Medicine> medicines;
    private final String filePath = "src/main/resources/Medicine_List.csv";

    public MedicineRepository() {
        medicines = new ArrayList<>();
        loadMedicinesFromCSV(filePath);
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
                    Medicine medicine = new Medicine(name, stockLevel, lowStockAlert);
                    medicines.add(medicine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
    public void saveMedicineToCSV(Medicine medicine, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(medicine.getName()).append(",")
                    .append(String.valueOf(medicine.getStockLevel())).append(",")
                    .append(String.valueOf(medicine.getLowStockAlert())).append("\n");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Used by admin to add new medicine
     * @param medicine
     */
    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
        saveMedicineToCSV(medicine, filePath);
    }

    /**
     * Used by
     * @param name
     * @return
     */
    public Medicine getMedication(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }

    public void updateStockLevel(String name, int newStockLevel) {
        Medicine medicine = getMedication(name);
        if (medicine != null) {
            medicine.setStockLevel(newStockLevel);
            if (newStockLevel < medicine.getLowStockAlert()) {
                medicine.setRequested(true);
            } else {
                medicine.setRequested(false);
            }
        }
    }

    public void updateLowStockAlert(String name, int newLowStockAlert) {
        Medicine medicine = getMedication(name);
        if (medicine != null) {
            medicine.setLowStockAlert(newLowStockAlert);
            if (medicine.getStockLevel() < newLowStockAlert) {
                medicine.setRequested(true);
            } else {
                medicine.setRequested(false);
            }
        }
    }

    public void checkAndRequestReplenishment() {
        for (Medicine medicine : medicines) {
            if (medicine.getStockLevel() < medicine.getLowStockAlert() && !medicine.isRequested()) {
                medicine.setRequested(true);
                System.out.println("Replenishment requested for: " + medicine.getName());
            }
        }
    }

    public List<Medicine> getMedicines() {
        return new ArrayList<>(medicines);
    }

    public boolean medicineExists(String medicineName) {
        return getMedication(medicineName) != null;
    }
}
