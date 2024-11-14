package org.example.repository;

import org.example.entity.Medicine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This can also can be interpreted conceptually as the Medicine Inventory.
 * This class is responsible for managing the stock of medicines in the clinic.
 */
public class MedicineRepository {
    private List<Medicine> medicines;
    private String filePath;

    /**
     * Constructor to inject dependencies
     * @param filePath
     */
    public MedicineRepository(String filePath) {
        this.filePath = filePath;
        this.medicines = new ArrayList<>();
        loadMedicinesFromCSV(filePath);
    }

    /**
     * Load medicines from CSV file
     * Parse medicine data from CSV file and store in medicines list
     * @param filePath
     */
    public void loadMedicinesFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String name = values[0].trim();
                    int stockLevel = Integer.parseInt(values[1].trim());
                    int lowThreshold = Integer.parseInt(values[2].trim());
                    int highThreshold = Integer.parseInt(values[3].trim());
                    Medicine medicine = new Medicine(name, stockLevel, lowThreshold, highThreshold);
                    medicines.add(medicine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    /**
     * Overwrite the CSV file with the most up-to-date list of medicines
     */
    private void saveMedicinesToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Medicine medicine : medicines) {
                writer.write(medicine.getName() + ","
                        + medicine.getStockLevel() + ","
                        + medicine.getLowThreshold() + ","
                        + medicine.getHighThreshold());
                writer.newLine();
            }
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
        saveMedicinesToCSV();
    }

    /**
     * Get all medicines in inventory
     * @return List of medicines
     */
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines);
    }

    /**
     * Get medicine by name
     * @param name
     * @return
     */
    public Medicine getMedicine(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }

    /**
     * Remove medicine from inventory
     * @param medicineName
     */
    public void removeMedicine(String medicineName) {
        medicines.remove(getMedicine(medicineName));
        saveMedicinesToCSV();
    }



    /**
     * Check if the medicine exists in the inventory
     * @param medicineName
     * @return true if the medicine exists, false otherwise
     */
    public boolean medicineExists(String medicineName) {
        return getMedicine(medicineName) != null;
    }

    /**
     * Decrease the stock level of a medicine
     * @param presciptionName
     * @param quantity
     */
    public void decreaseStockLevel(String presciptionName, int quantity) {
        Medicine medicine = getMedicine(presciptionName);
        if (medicine != null) {
            medicine.setStockLevel(medicine.getStockLevel() - quantity);
            saveMedicinesToCSV();
        }
    }

    /**
     * Get all medicines that are low in stock
     * @return List of low stock medicines
     */
    public List<Medicine> getLowStockMedicines() {
        return medicines.stream()
                .filter(Medicine::isLowStock)
                .toList();
    }

    public boolean hasLowStockMedicines() {
        return medicines.stream().anyMatch(Medicine::isLowStock);
    }

    /**
     * Update stock level of medicine
     * @param medicineName, quantity
     */
    public void updateStockLevel(String medicineName, int quantity) {
        Medicine medicine = getMedicine(medicineName);
        if (medicine != null) { // run anyway, cuz check before. Can delete the if statement
            medicine.setStockLevel(quantity);
            saveMedicinesToCSV();
        }
    }


    /**
     * Update the low threshold of a medicine
     * @param medicineName
     * @param lowThreshold
     */
    public void updateLowThreshold(String medicineName, int lowThreshold) {
        Medicine medicine = getMedicine(medicineName);
        if (medicine != null) {
            medicine.setLowThreshold(lowThreshold);
            saveMedicinesToCSV();
        }
    }
}
