package org.example.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecord {
    private int appointmentId;
    private LocalDate date;
    private List<String> typeOfService;
    private String consultationNotes;
    private List<Medication> medications;


    public AppointmentOutcomeRecord(int appointmentId, LocalDate date, String consultationNotes, List<String> typeOfService, List<Medication> medications) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.consultationNotes = consultationNotes;
        this.typeOfService = typeOfService;
        this.medications = medications;
    }

    public List<Medication> getMedications() {
        return medications;
    }
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void addMedication(Medication medication) {
        this.medications.add(medication);
    }
    public void addMedication(String name, int quantity) {
        Medication medication = new Medication(appointmentId, name, quantity);
        this.medications.add(medication);
    }

    public void addTypeOfService(String typeOfService) {
        this.typeOfService.add(typeOfService);
    }

    public void deleteTypeOfService(String typeOfService) {
        this.typeOfService.remove(typeOfService);
    }

    public List<String> getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(ArrayList<String> typeOfService) {
        this.typeOfService = typeOfService;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDate() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public boolean updateMedicationStatus(String medicationName, Medication.Status status) {
        for (Medication medication : medications) {
            if (medication.getName().equals(medicationName)) {
                medication.setStatus(Medication.Status.valueOf(status.name()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AppointmentOutcomeRecord{" +
                "appointmentId=" + appointmentId +
                ", date='" + date + '\'' +
                ", consultationNotes='" + consultationNotes + '\'' +
                ", typeOfService=" + typeOfService +
                ", Prescription:" +
                " medications=" + medications +
                '}';
    }
}