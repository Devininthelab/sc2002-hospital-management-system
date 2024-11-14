package org.example.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecord {
    private int appointmentId;
    private String date;
    private List<String> typeOfService;
    private String consultationNotes;
    private List<Prescription> prescriptions;


    public AppointmentOutcomeRecord(int appointmentId, LocalDate date, String consultationNotes, List<String> typeOfService, List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.consultationNotes = consultationNotes;
        this.typeOfService = typeOfService;
        this.prescriptions = prescriptions;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }
    public void addPrescription(String name, int quantity) {
        Prescription prescription = new Prescription(appointmentId, name, quantity);
        this.prescriptions.add(prescription);
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

    public boolean updateMedicationStatus(String medicationName, Prescription.Status status) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getName().equals(medicationName)) {
                prescription.setStatus(Prescription.Status.valueOf(status.name()));
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
                " prescriptions=" + prescriptions +
                '}';
    }
}