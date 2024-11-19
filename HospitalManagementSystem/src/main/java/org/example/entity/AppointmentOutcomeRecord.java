package org.example.entity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents an appointment outcome record, including details about
 * the appointment ID, date, consultation notes, types of services
 * provided, and prescriptions issued during the appointment.
 */
public class AppointmentOutcomeRecord {
    private int appointmentId;
    private String date;
    private List<String> typeOfService;
    private String consultationNotes;
    private List<Prescription> prescriptions;

    /**
     * Constructor for creating an appointment outcome record
     *
     * @param appointmentId the ID of the appointment
     * @param date the date of the appointment
     * @param consultationNotes the consultation notes of the appointment
     * @param typeOfService the list of types of services provided during the appointment
     * @param prescriptions the list of prescriptions given during the appointment
     */
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


    /**
     * Set the prescription of the appointment outcome record
     */
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    /**
     * Add a prescription to the appointment outcome record
     * @param prescription the prescription to add
     */
    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }

    /**
     * Add a prescription to the appointment outcome record
     * @param name the name of the prescription
     * @param quantity the quantity of the prescription
     */
    public void addPrescription(String name, int quantity) {
        Prescription prescription = new Prescription(appointmentId, name, quantity);
        this.prescriptions.add(prescription);
    }

    /**
     * Adds a type of service to the appointment outcome record.
     *
     * @param typeOfService the type of service to add
     */
    public void addTypeOfService(String typeOfService) {
        this.typeOfService.add(typeOfService);
    }

    /**
     * Deletes a type of service from the appointment outcome record.
     *
     * @param typeOfService the type of service to delete
     */
    public void deleteTypeOfService(String typeOfService) {
        this.typeOfService.remove(typeOfService);
    }

    /**
     * Retrieves the list of types of services provided during the appointment.
     *
     * @return a list of types of services
     */
    public List<String> getTypeOfService() {
        return typeOfService;
    }

    /**
     * Retrieves the types of services as a single string, joined by semicolons.
     *
     * @return a string representation of the types of services
     */
    public String getTypeOfServiceString() {
        return String.join(";", typeOfService);
    }

    /**
     * Sets the list of types of services for the appointment outcome record.
     *
     * @param typeOfService the list of types of services to set
     */
    public void setTypeOfService(ArrayList<String> typeOfService) {
        this.typeOfService = typeOfService;
    }

    /**
     * Retrieves the appointment ID.
     *
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Retrieves the date of the appointment.
     *
     * @return the appointment date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the consultation notes for the appointment.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the consultation notes for the appointment.
     *
     * @param consultationNotes the consultation notes to set
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Updates the status of a prescription based on its name.
     *
     * @param medicationName the name of the medication
     * @param status         the status to set
     * @return true if the status was updated successfully; false otherwise
     */
    public boolean updateMedicationStatus(String medicationName, String status) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getName().equals(medicationName)) {
                prescription.setStatus(status);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the appointment outcome record,
     * including appointment details, types of services, and prescriptions.
     *
     * @return a string representation of the appointment outcome record
     */
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
        return "Appointment Outcome Record\n - Appointment ID: " + appointmentId +
                "\n - Date: " + date +
                "\n - ConsultationNotes: " + consultationNotes +
                "\n - TypeOfService: \n" + typeOfServiceString +
                " - Prescription: \n" + prescriptionString;
    }
}