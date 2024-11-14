package org.example.repository;

import org.example.entity.Prescription;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository {
    private List<Prescription> prescriptions = new ArrayList<>();
    private final String filePath = "src/main/resources/Prescriptions.csv";

    public void loadMedicationsFromCSV() {
        String line;
        prescriptions.clear(); // Clear list before loading to avoid duplicates

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int medId = Integer.parseInt(values[0].trim());
                    String name = values[1].trim();
                    int quantity = Integer.parseInt(values[2].trim());
                    Prescription.Status status = Prescription.Status.valueOf(values[3].trim().toUpperCase());

                    Prescription prescription = new Prescription(medId, name, quantity, status);
                    prescriptions.add(prescription);
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
            for (Prescription prescription : prescriptions) {
                bw.write(prescription.getId() + "," + prescription.getName() + "," +
                        prescription.getQuantity() + "," + prescription.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public List<Prescription> getMedicationsById(int prescriptionId) {
        List<Prescription> result = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == prescriptionId) {
                result.add(prescription);
            }
        }
        return result;
    }

    // Additional method to add a new medication and save to CSV
    public void addMedication(Prescription prescription) {
        prescriptions.add(prescription);
        saveMedicationsToCSV(); // Save changes after adding
    }

    public void addMedication(int id, String name, int quantity) {
        Prescription prescription = new Prescription(id, name, quantity);
        prescriptions.add(prescription);
        saveMedicationsToCSV();
    }
}
