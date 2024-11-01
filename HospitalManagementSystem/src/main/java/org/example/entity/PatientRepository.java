package org.example.entity;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientRepository {
    private List<Patient> patients = new ArrayList<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String csvPath = "src/main/resources/patientdb.csv";

    public PatientRepository() {
        this.patients = new ArrayList<>();
        loadPatientsFromCSV(csvPath);
    }

    private void loadPatientsFromCSV(String csvPath) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0].trim());
                String name = values[1].trim();
                LocalDate dateOfBirth = LocalDate.parse(values[2].trim(), DATE_FORMATTER);
                Patient.Gender gender = Patient.Gender.valueOf(values[3].trim().toUpperCase());
                Patient.BloodType bloodType = Patient.BloodType.valueOf(values[4].trim().toUpperCase());

                this.patients.add(new Patient(id, name, dateOfBirth, gender, bloodType));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number");
        }
    }

    public Patient getPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    public void updatePatientField(int id, String field, String newValue) {
        Patient patient = getPatientById(id);
        if (patient == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }

        // Update the specified field based on its name
        switch (field.toLowerCase()) {
            case "name":
                patient.setName(newValue);
                break;
            case "dateofbirth":
                patient.setDateOfBirth(LocalDate.parse(newValue, DATE_FORMATTER));
                break;
            case "gender":
                patient.setGender(Patient.Gender.valueOf(newValue.toUpperCase()));
                break;
            case "bloodtype":
                patient.setBloodType(Patient.BloodType.valueOf(newValue.toUpperCase().replace("+", "_POSITIVE").replace("-", "_NEGATIVE")));
                break;
            default:
                System.out.println("Field not recognized.");
                return;
        }

        // Save the updated list of patients back to the CSV
        savePatientsToCSV();
    }

    private void savePatientsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
            for (Patient patient : patients) {
                bw.write(patient.getId() + "," + patient.getName() + "," +
                        DATE_FORMATTER.format(patient.getDateOfBirth()) + "," +
                        patient.getGender() + "," +
                        patient.getBloodType() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
