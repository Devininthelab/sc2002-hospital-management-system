package org.example.repository;

import org.example.entity.Medicine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is also can be know as InventoryRepository. This class is responsible for managing the stock of medicines in the clinic.
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

    private void saveMedicinesToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Medicine medicine : medicines) {
                writer.write(medicine.getName() + ","
                        + medicine.getStockLevel() + ","
                        + medicine.getLowStockAlert());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
        saveMedicinesToCSV();
    }

    public Medicine getMedicine(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }
    public void removeMedicine(Medicine medicine) {
        medicines.remove(medicine);
        saveMedicinesToCSV();
    }

    public void updateStockLevel(String name, int newStockLevel) {
        Medicine medicine = getMedicine(name);
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
        Medicine medicine = getMedicine(name);
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
        return getMedicine(medicineName) != null;
    }
}
