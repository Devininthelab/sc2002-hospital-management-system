package org.example.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class AppointmentOutcomeRecord {
    private int appointmentOutcomeRecordId;
    private LocalDate dateOfAppointment;
    private String typeOfService;   // x-ray, blood test, etc
    private List<Medicine> prescribedMedications;
    private String consultationNotes;


    public AppointmentOutcomeRecord(int appointmentOutcomeRecordId, LocalDate dateOfAppointment, String typeOfService, String consultationNotes) {
        this.appointmentOutcomeRecordId = appointmentOutcomeRecordId;
        this.dateOfAppointment = dateOfAppointment;
        this.typeOfService = typeOfService;
        this.consultationNotes = consultationNotes;
        this.prescribedMedications = new ArrayList<>();
    }

    public void addMedication(Medicine medication) {
        prescribedMedications.add(medication);
    }

    // Getters and Setters
    public int getAppointmentId() {
        return appointmentOutcomeRecordId;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    public String getServiceType() {
        return typeOfService;
    }

    public List<Medicine> getPrescribedMedications() {
        return prescribedMedications;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    // toString method
    @Override
    public String toString() {
        return "AppointmentOutcomeRecord{" +
                "appointmentId=" + appointmentOutcomeRecordId +
                ", dateOfAppointment=" + dateOfAppointment +
                ", serviceType='" + typeOfService + '\'' +
                ", prescribedMedications=" + prescribedMedications +
                ", consultationNotes='" + consultationNotes + '\'' +
                '}';
    }
}
