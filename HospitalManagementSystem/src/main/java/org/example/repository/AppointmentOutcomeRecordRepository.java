package org.example.repository;

import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Prescription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Repository class for AppointmentOutcomeRecord objects</p>
 * <p>This class is responsible for loading and saving records to a CSV file and providing methods to query the records</p>
 * <p>Record also contains a list of prescriptions, which uses the PrescriptionRepository</p>
 */
public class AppointmentOutcomeRecordRepository {
    private List<AppointmentOutcomeRecord> records;
    private PrescriptionRepository prescriptionRepository;
    private String filePath;

    /**
     * Constructor
     * @param filePath WRITABLE Path to the CSV file
     * @param prescriptionRepository PrescriptionRepository object
     */
    public AppointmentOutcomeRecordRepository(String filePath, PrescriptionRepository prescriptionRepository) {
        // Load prescriptions and records from their respective CSV files
        records = new ArrayList<>();
        this.filePath = filePath;
        this.prescriptionRepository = prescriptionRepository;
        loadRecordsFromCSV();
    }

    /**
     * <p>Load records from CSV file</p>
     * <p>This method is called by the constructor</p>
     * <p>Before reading the files line by line, it first checks if the file exists in the writable directory</p>
     * <p>If not, it loads the file from the resources directory and saves it to the writable directory</p>
     * <p>Exceptions are caught and printed to the console</p>
     */
    private void loadRecordsFromCSV() {
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

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                String header = br.readLine(); // Skip header
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");

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

            if (loadedFromResources) {
                saveRecordsToCSV();
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        //String line;
        //try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        //    String header = br.readLine(); // Skip header
        //    while ((line = br.readLine()) != null) {
        //        String[] values = line.split(",");
        //
        //        int id = Integer.parseInt(values[0].trim());
        //        String date = values[1].trim();
        //        String typeOfServiceStr = values[2].trim();
        //        String consultationNotes = values[3].trim();
        //
        //        // Deserialize typeOfService string
        //        String[] types = typeOfServiceStr.split(";");
        //        ArrayList<String> typeOfService = new ArrayList<>();
        //        for (String type : types) {
        //            typeOfService.add(type.trim());
        //        }
        //
        //        // Retrieve prescriptions for this record and assign them
        //        List<Prescription> prescriptions = prescriptionRepository.getPrescriptionsById(id);  // Updated method name
        //        // Create the AppointmentOutcomeRecord
        //        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(id, date, consultationNotes, typeOfService, prescriptions);
        //
        //        records.add(record); // Add to records list
        //
        //    }
        //} catch (IOException e) {
        //    System.out.println("Error reading CSV file: " + e.getMessage());
        //}
    }

    /**
     * Save records to CSV file
     * This method is called whenever a record is added or updated
     * It writes all records to the file, overwriting the previous content
     * Exceptions are caught and printed to the console
     */
    public void saveRecordsToCSV() {
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

    /**
     * Get all records
     * @return List of AppointmentOutcomeRecord objects
     */
    public List<AppointmentOutcomeRecord> getAllPendingRecords() {
        return records;
    }

    /**
     * Query the records for matching id
     * @param id Appointment ID
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

    /**
     * Add a new record to the repository
     * @param record AppointmentOutcomeRecord object
     */
    public void addAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        records.add(record);
        saveRecordsToCSV();
    }

    /**
     * Read record by appointment ID
     * @param appointmentId Appointment ID
     * @return AppointmentOutcomeRecord object, or null if not found
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