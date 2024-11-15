package org.example.repository;

import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Prescription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecordRepository {
    private List<AppointmentOutcomeRecord> records;
    private PrescriptionRepository prescriptionRepository;
    private String filePath = "src/main/resources/AppointmentOutcomeRecord.csv";

    public AppointmentOutcomeRecordRepository(String filePath, PrescriptionRepository prescriptionRepository) {
        // Load prescriptions and records from their respective CSV files
        records = new ArrayList<>();
        this.prescriptionRepository = prescriptionRepository;
        loadRecordsFromCSV();
    }

    /**
     * Load records from CSV file
     */
    private void loadRecordsFromCSV() {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    int id = Integer.parseInt(values[0].trim());
                    String date = values[1].trim();
                    String typeOfServiceStr = values[2].trim();
                    String consultationNotes = values[3].trim();

                    // Deserialize typeOfService string
                    String[] types = typeOfServiceStr.split(";");
                    ArrayList<String> typeOfService = new ArrayList<>();
                    for (String type : types) {
                        typeOfService.add(type.trim());
                    }

                    // Retrieve prescriptions for this record and assign them
                    List<Prescription> prescriptions = prescriptionRepository.getPrescriptionsById(id);  // Updated method name
                    // Create the AppointmentOutcomeRecord
                    AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(id, date, consultationNotes, typeOfService, prescriptions);

                    records.add(record); // Add to records list
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Method to save a record to CSV
    public void saveRecordsToCSV(AppointmentOutcomeRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("appointmentId,date,typeOfService,consultationNotes");
            writer.newLine();

            // Write all records
            for (AppointmentOutcomeRecord rec : records) {
                writer.write(rec.getAppointmentId() + "," + rec.getDate() + "," + rec.getTypeOfServiceString() + "," + rec.getConsultationNotes());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
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

    /**
     * Get appointment outcome record by appointment ID
     */
    public AppointmentOutcomeRecord getAppointmentOutcomeRecordById(int appointmentId) {
        for (AppointmentOutcomeRecord record : records) {
            if (record.getAppointmentId() == appointmentId) {
                return record;
            }
        }
        return null;  // Return null if record not found
    }
}