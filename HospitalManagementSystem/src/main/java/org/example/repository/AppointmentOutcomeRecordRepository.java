package org.example.repository;
import org.example.entity.AppointmentOutcomeRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentOutcomeRecordRepository {
    private List<AppointmentOutcomeRecord> records = new ArrayList<>();
    private String filePath;

    public AppointmentOutcomeRecordRepository(String filePath) {
        this.filePath = filePath;
        loadRecordsFromCSV();
    }

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
                    String prescriptionStr = values[4].trim();
                    String consultationNotes = values[5].trim();

                    // Deserialize prescription CSV-style string
                    HashMap<String, List<Object>> prescription = parsePrescription(prescriptionStr);

                    // Deserialize typeOfService string
                    String[] types = typeOfServiceStr.split(";");
                    ArrayList<String> typeOfService = new ArrayList<>();
                    for (String type : types) {
                        typeOfService.add(type.trim());
                    }

                    // Create the AppointmentOutcomeRecord
                    AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(id, date, timeslot, consultationNotes, prescription, typeOfService);
                    records.add(record);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing record data: " + e.getMessage());
        }
    }
    private HashMap<String, List<Object>> parsePrescription(String prescriptionStr) {
        HashMap<String, List<Object>> prescription = new HashMap<>();
        String[] entries = prescriptionStr.split(";");

        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String[] values = keyValue[1].split(",");
                List<Object> valueList = new ArrayList<>();
                for (String value : values) {
                    valueList.add(value.trim());
                }
                prescription.put(key, valueList);
            }
        }

        return prescription;
    }
    private String convertPrescriptionToCSVStyle(HashMap<String, List<Object>> prescription) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Object>> entry : prescription.entrySet()) {
            sb.append(entry.getKey()).append(":");
            sb.append(String.join(",", (CharSequence[]) entry.getValue().toArray(new String[0])));
            sb.append(";");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove the trailing semicolon
        }
        return sb.toString();
    }
    public void saveRecordToCSV(AppointmentOutcomeRecord record) {
        //IMPLEMENT LATER
    }
    public List<AppointmentOutcomeRecord> getRecords() {
        return records;
    }
    public void displayRecords() {
        records.forEach(System.out::println);
    }

    // Get record by ID
    public AppointmentOutcomeRecord getRecordById(int id) {
        for (AppointmentOutcomeRecord record : records) {
            if (record.getAppointmentId() == id) {
                return record;
            }
        }
        return null; // If not found
    }
}