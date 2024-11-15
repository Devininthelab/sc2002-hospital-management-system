package org.example.entity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecord {
    private int appointmentId;
    private String date;
    private List<String> typeOfService;
    private String consultationNotes;
    private List<Prescription> prescriptions; //TO DO: consider how to display this one, as we do not store prescriptions
    // in the same database as AppointmentOutcomeRecord


    public AppointmentOutcomeRecord(int appointmentId, String date, String consultationNotes, List<String> typeOfService, List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.consultationNotes = consultationNotes;
        this.typeOfService = typeOfService;
        this.prescriptions = prescriptions;
    }

    /**
     * Get All Prescirptions regarding the appointmentOutcomeRecord
     * @return a list of presciptions
     */
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

    public String getTypeOfServiceString() {
        return String.join(";", typeOfService);
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
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public boolean updateMedicationStatus(String medicationName, String status) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getName().equals(medicationName)) {
                prescription.setStatus(status);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String typeOfServiceString = "";
        for (String service : typeOfService) {
            typeOfServiceString += "   - " + service + "\n";
        }
        String prescriptionString = "";
        for (Prescription prescription : prescriptions) {
            prescriptionString += "   - " + prescription.toString() + "\n";
        }
        return "Appointment Outcome Record - Appointment ID: " + appointmentId +
                "\n - Date: " + date +
                "\n - ConsultationNotes: " + consultationNotes +
                "\n - TypeOfService: \n" + typeOfServiceString +
                " - Prescription: \n" + prescriptionString;
    }
}