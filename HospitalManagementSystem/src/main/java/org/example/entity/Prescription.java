package org.example.entity;

import java.util.HashMap;
import java.util.Map;

public class Prescription {
    public enum Status {
        PENDING, DISPENSED;
    }
    private int prescriptionId;
    private String patientId;
    private String doctorId;
    private Map<String, Status> medications; // Maps medication name to status

    public Prescription(int prescriptionId, String patientId, String doctorId) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medications = new HashMap<>();
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void addMedication(String medicationName, Status status) {
        medications.put(medicationName, status);
    }

    public Map<String, Status> getMedications() {
        return medications;
    }

    public void updateMedicationStatus(String medicationName, Status status) {
        if (medications.containsKey(medicationName)) {
            medications.put(medicationName, status);
        } else {
            System.out.println("Medication not found.");
        }
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId=" + prescriptionId +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", medications=" + medications +
                '}';
    }
}
