package org.example.repository;

import org.example.entity.Medicine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This can also can be interpreted conceptually as the Medicine Inventory.
 * This class is responsible for managing the stock of medicines in the clinic.
 * It reads from the medicine database and updates the stock level of medicines.
 * It also provides methods to check the stock level of medicines and update the threshold.
 */
public class MedicineRepository {
    private List<Medicine> medicines;
    private String filePath;

    /**
     * Constructor to inject dependencies
     * @param filePath - the writable path to the medicine database
     */
    public MedicineRepository(String filePath) {
        this.filePath = filePath;
        this.medicines = new ArrayList<>();
        loadMedicinesFromCSV(filePath);
    }

    /**
     * Load medicines from CSV file
     * If the file exists in the writable path, it will load from the file
     * If the file does not exist, it will load from the resources folder
     * Parse medicine data from CSV file and store in medicines list
     * @param filePath - the path to the CSV file
     */
    public void loadMedicinesFromCSV(String filePath) {
        InputStream inputStream;
        boolean loadedFromResources = false;
        try {
            File writableFile = new File(filePath);
            if (writableFile.exists()) {
                inputStream = new FileInputStream(filePath);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(new File(filePath).getName());
                if (inputStream == null) {
                    System.err.println("File not found: " + filePath);
                    return;
                }
                loadedFromResources = true;
            }

            String line;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String header = br.readLine(); // Skip header
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
            }

            if (loadedFromResources) {
                saveMedicinesToCSV();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        //String line;
        //try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        //    String header = br.readLine(); // Skip header
        //    while ((line = br.readLine()) != null) {
        //        String[] values = line.split(",");
        //        if (values.length >= 3) {
        //            String name = values[0].trim();
        //            int stockLevel = Integer.parseInt(values[1].trim());
        //            int lowThreshold = Integer.parseInt(values[2].trim());
        //            int highThreshold = Integer.parseInt(values[3].trim());
        //            Medicine medicine = new Medicine(name, stockLevel, lowThreshold, highThreshold);
        //            medicines.add(medicine);
        //        }
        //    }
        //} catch (IOException e) {
        //    System.out.println("Error reading CSV file: " + e.getMessage());
        //}
    }

    /**
     * Overwrite the CSV file with the most up-to-date list of medicines
     * This method is called whenever a change is made to the list of medicines
     */
    private void saveMedicinesToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("name,stock,low_threshold,high_threshold\n");
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
     * @param medicine - the medicine to be added
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
     * @param name - the name of the medicine
     * @return Medicine object with the specified name
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
     * @param medicineName - the name of the medicine to be removed
     */
    public void removeMedicine(String medicineName) {
        medicines.remove(getMedicine(medicineName));
        saveMedicinesToCSV();
    }



    /**
     * Check if the medicine exists in the inventory
     * @param medicineName - the name of the medicine
     * @return true if the medicine exists, false otherwise
     */
    public boolean medicineExists(String medicineName) {
        return getMedicine(medicineName) != null;
    }

    /**
     * Decrease the stock level of a medicine
     * @param presciptionName - the name of the medicine
     * @param quantity - the quantity to be decreased
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

    /**
     * Check if there are any low stock medicines
     * @return true if there are low stock medicines, false otherwise
     */
    public boolean hasLowStockMedicines() {
        return medicines.stream().anyMatch(Medicine::isLowStock);
    }

    /**
     * Update the stock level of a medicine
     * @param medicineName - the name of the medicine
     * @param quantity - the new stock level
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
     * @param medicineName - the name of the medicine
     * @param lowThreshold - the new low threshold
     */
    public void updateLowThreshold(String medicineName, int lowThreshold) {
        Medicine medicine = getMedicine(medicineName);
        if (medicine != null) {
            medicine.setLowThreshold(lowThreshold);
            saveMedicinesToCSV();
        }
    }
}
