package org.example.repository;

import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Prescription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecordRepository {
    private List<AppointmentOutcomeRecord> records = new ArrayList<>();
    private PrescriptionRepository prescriptionRepository = new PrescriptionRepository();
    private static final String filePath = "src/main/resources/AppointmentOutcomeRecord.csv";

    public AppointmentOutcomeRecordRepository() {
        // Load medications and records from their respective CSV files
        prescriptionRepository.loadMedicationsFromCSV();
        loadRecordsFromCSV();
    }

    /**
     * Load records from CSV file
     */
    private void loadRecordsFromCSV() {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    int id = Integer.parseInt(values[0].trim());
                    String date = values[1].trim();
                    int timeslot = Integer.parseInt(values[2].trim());
                    String typeOfServiceStr = values[3].trim();
                    String consultationNotes = values[5].trim();

                    // Deserialize typeOfService string
                    String[] types = typeOfServiceStr.split(";");
                    ArrayList<String> typeOfService = new ArrayList<>();
                    for (String type : types) {
                        typeOfService.add(type.trim());
                    }

                    // Create the AppointmentOutcomeRecord
                    AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(id, date, timeslot, consultationNotes, typeOfService);

                    // Retrieve medications for this record and assign them
                    List<Prescription> prescriptions = prescriptionRepository.getMedicationsById(id);
                    record.setMedications(prescriptions);

                    records.add(record); // Add to records list
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Method to save a record to CSV
    public void saveRecordsToCSV(AppointmentOutcomeRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Convert record fields into a CSV string
            String csvLine = recordToCSV(record);

            // Write the new record to the CSV file
            writer.write(csvLine);
            writer.newLine(); // Ensure the next record goes on a new line
        } catch (IOException e) {
            System.out.println("Error saving record to CSV: " + e.getMessage());
        }
    }

    // Helper method to convert AppointmentOutcomeRecord to CSV format
    private String recordToCSV(AppointmentOutcomeRecord record) {
        StringBuilder csvBuilder = new StringBuilder();

        // Append fields, separated by commas
        csvBuilder.append(record.getAppointmentId()).append(",");
        csvBuilder.append(record.getDate()).append(",");
        csvBuilder.append(record.getTimeslot()).append(",");

        // Convert list of typeOfService to a semicolon-separated string
        csvBuilder.append(String.join(";", record.getTypeOfService())).append(",");

        // Placeholder for medication list to simplify CSV structure (if needed)
        csvBuilder.append("\"");
        for (Prescription med : record.getMedications()) {
            csvBuilder.append(med.getName()).append(";");
        }
        csvBuilder.deleteCharAt(csvBuilder.length() - 1); // Remove last semicolon
        csvBuilder.append("\"");

        csvBuilder.append(",").append(record.getConsultationNotes());

        return csvBuilder.toString();
    }

    public List<AppointmentOutcomeRecord> getAllPendingRecords() {
        return records;
    }

    /**
     * Query the records for matching id
     * @param id
     * @return AppointmentOutcomeRecord object, or null if
     */
    public AppointmentOutcomeRecord getRecordById(int id) {
        for (AppointmentOutcomeRecord record : records) {
            if (record.getAppointmentId() == id) {
                return record;
            }
        }
        return null; // If not found
    }

    public void addAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        records.add(record);
        saveRecordsToCSV(record);
    }

}