package org.example.repository;

import org.example.entity.Appointment;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Patient;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class for managing patients.
 */
public class PatientRepository {
    private List<Patient> patients;
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository = new AppointmentOutcomeRecordRepository();
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String csvPath = "src/main/resources/Patient_List.csv";

    /**
     * Constructor that initializes the list of patients by loading data from a CSV file.
     * TODO: Implement depedency injection for every dependencies
     */
    public PatientRepository() {
        this.patients = new ArrayList<>();
        loadPatientsFromCSV(csvPath);
    }

    /**
     * Load patients from a CSV file.
     * The List of patients will be stored in the patients field.
     * Diagnosis, treatment and prescription is parsed using ; as delimiter.
     * @param csvPath the path to the CSV file
     */
    private void loadPatientsFromCSV(String csvPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0].trim();
                String password = values[1].trim();
                String name = values[2].trim();
                LocalDate dateOfBirth = LocalDate.parse(values[3].trim(), DATE_FORMATTER);
                String gender = values[4].trim();
                String contact = values[5].trim();
                String bloodType = values[6].trim().toUpperCase();
                Patient patient = new Patient(id, password, name, dateOfBirth, gender, contact, bloodType);
                // load diagnoses and treatment
                List<String> diagnoses = Arrays.asList(values[7].trim().split(";"));
                List<String> treatment = Arrays.asList(values[8].trim().split(";"));
                List<String> prescription = Arrays.asList(values[9].trim().split(";"));
                //IMPORTANT: no need to load appointment and outcome record here
                patient.setDiagnoses(diagnoses);
                patient.setTreatments(treatment);
                patient.setPrescriptions(prescription);
                patients.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number");
        }
    }

    /**
     * Get a list of all patients.
     * TODO: Consider encapsulating the list of patients by returning a deep copy of the list.
     * @return the list of patients
     */
    public Patient getPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * Update a patient's field with a new value.
     * The field is specified as a string, and the new value is also a string.
     * Some component needs to convert different types of values to string before calling this method.
     */
    public void updatePatientField(String id, String field, String newValue) {
        Patient patient = getPatientById(id);
        if (patient == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }

        // Update the specified field based on its name
        switch (field.toLowerCase()) {
            case "password":
                patient.setPassword(newValue);
                break;
            case "name":
                patient.setName(newValue);
                break;
            case "dateofbirth":
                patient.setDateOfBirth(LocalDate.parse(newValue, DATE_FORMATTER));
                break;
            case "gender":
                patient.setGender(newValue);
                break;
            case "bloodtype":
                patient.setBloodType(newValue);
                break;
            default:
                System.out.println("Field not recognized.");
                return;
        }

        // Save the updated list of patients back to the CSV
        savePatientsToCSV();
    }

    /**
     * Save the list of patients back to the CSV file.
     */
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
