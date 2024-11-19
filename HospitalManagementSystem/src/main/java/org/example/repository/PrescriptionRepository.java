package org.example.repository;

import org.example.entity.Prescription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PrescriptionRepository is responsible for managing the list of prescriptions.
 * It reads from the prescription database and updates the prescription's status.
 * It also provides methods to get a prescription by ID, update a prescription's status, and save the prescriptions back to the database.
 */
public class PrescriptionRepository {
    private List<Prescription> prescriptions;
    private String filePath;

    /**
     * Constructor to create a PrescriptionRepository.
     * It loads the prescriptions from the CSV file.
     * @param prescriptionPath The writable path to the prescription database.
     */
    public PrescriptionRepository(String prescriptionPath) {
        this.filePath = prescriptionPath;
        this.prescriptions = new ArrayList<>();
        loadPrescriptionsFromCSV();
    }

    /**
     * Load Prescriptions from CSV file.
     * If the file exists in the writable path, it will load from the file.
     * If the file does not exist, it will load from the resources folder.
     * Parses prescription data from the CSV file and stores it in the prescriptions list.
     */
    public void loadPrescriptionsFromCSV() {
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
            prescriptions.clear(); // Clear list before loading to avoid duplicates

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String header = br.readLine(); // Skip header
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
            }

            if (loadedFromResources) {
                savePrescriptionsToCSV();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error parsing Prescription data: " + e.getMessage());
        }

        //String line;
        //prescriptions.clear(); // Clear list before loading to avoid duplicates
        //
        //try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        //    String header = br.readLine(); // Skip header
        //    while ((line = br.readLine()) != null) {
        //        String[] values = line.split(",");
        //        if (values.length >= 4) {
        //            int medId = Integer.parseInt(values[0].trim());
        //            String name = values[1].trim();
        //            int quantity = Integer.parseInt(values[2].trim());
        //            String status = values[3].trim().toUpperCase();
        //            Prescription prescription = new Prescription(medId, name, quantity, status);
        //            prescriptions.add(prescription);
        //        }
        //    }
        //} catch (IOException e) {
        //    System.out.println("Error reading CSV file: " + e.getMessage());
        //} catch (Exception e) {
        //    System.out.println("Error parsing Prescription data: " + e.getMessage());
        //}
    }

    /**
     * Overwrite the CSV file with the most up-to-date list of Prescriptions.
     * Writes the Prescriptions to the writable path.
     * Saves the current list of prescriptions to the CSV file.
     */
    public void savePrescriptionsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("appointmentId,name,dosage,status");
            bw.newLine();
            for (Prescription prescription : prescriptions) {
                bw.write(prescription.getId() + "," + prescription.getName() + "," +
                        prescription.getQuantity() + "," + prescription.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Get a list of prescriptions by their unique ID.
     * This method searches for prescriptions in the repository based on their unique ID.
     * @param prescriptionId The unique ID of the prescription.
     * @return List<Prescription> A list of prescriptions matching the ID.
     */
    public List<Prescription> getPrescriptionsById(int prescriptionId) {
        List<Prescription> result = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == prescriptionId) {
                result.add(prescription);
            }
        }
        return result;
    }

    /**
     * Add a new prescription to the repository and save to CSV.
     * @param prescription The prescription object to be added.
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        savePrescriptionsToCSV(); // Save changes after adding
    }

    /**
     * Retrieve a prescription by its ID and name.
     * This method searches for a prescription in the repository based on its unique
     * ID and name, and returns the matching prescription if found.
     * @param prescriptionId The unique ID of the prescription.
     * @param name The name of the prescription.
     * @return Prescription The prescription matching the given ID and name, or null if not found.
     */
    public Prescription getPrescriptionsByNameAndId(int prescriptionId, String name) {
        for (Prescription Prescription : prescriptions) {
            if (Prescription.getId() == prescriptionId && Prescription.getName().equalsIgnoreCase(name)) {
                return Prescription;
            }
        }
        return null;
    }

    /**
     * Update the status of an existing prescription.
     * This method updates the status of a prescription in the repository based on its unique ID and name.
     * @param id The ID of the prescription to update.
     * @param PrescriptionName The name of the prescription to update.
     * @param status The new status to set (e.g., PENDING, DISPENSED).
     */
    public void updatePrescriptionStatus(int id, String PrescriptionName, String status) {
        Prescription Prescription = getPrescriptionsByNameAndId(id, PrescriptionName);
        if (Prescription != null) {
            Prescription.setStatus(status.toUpperCase());
        }
        savePrescriptionsToCSV();
    }

    /**
     * Get a list of pending prescriptions for a specific appointment ID.
     * This method returns a list of prescriptions with a "PENDING" status for a given appointment ID.
     * @param id The appointment ID for which pending prescriptions are needed.
     * @return List<Prescription> A list of prescriptions with a "PENDING" status.
     */
    public List<Prescription> getPendingPrescriptions(int id) {
        List<Prescription> notDispensed = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == id && prescription.getStatus().equals("PENDING")) {
                notDispensed.add(prescription);
            }
        }
        return notDispensed;
    }
}
