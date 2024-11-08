


package org.example.entity;

import org.example.repository.MedicationRepository;

import java.util.List;

public class Prescription {
    private int prescriptionId;
    private List<Medication> medications;
    private final MedicationRepository repo = new MedicationRepository();

    // Constructor
    public Prescription(int prescriptionId) {
        this.prescriptionId = prescriptionId;
        repo.loadMedicationsFromCSV();
        this.medications = repo.getMedicationsById(prescriptionId);
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    public void updateMedicationStatus(String medicationName, Medication.Status status) {
        for (Medication medication : medications) {
            if (medication.getName().equals(medicationName)) {
                medication.setStatus(status);
                return;
            }
        }
        System.out.println("Medication not found.");
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId=" + prescriptionId +
                ", medications=" + medications +
                '}';
    }
}