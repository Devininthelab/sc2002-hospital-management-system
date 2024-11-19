package org.example.entity;

import org.example.utils.TimeslotToInt;

import java.time.LocalDate;

/**
 * <p>Entity class for Appointment objects</p>
 * <p>Appointments are created by patients and can be accepted or rejected by doctors</p>
 * <p>Appointments can be rescheduled by patients</p>
 */
public class Appointment {
    private int id;
    private String doctorId;
    private String patientId;
    private String date;
    private int timeslot; // 8 hours a day, 6 days a week = 8 x 6 = 48 timeslots ranging from 0 to 47
    private String status;
    private AppointmentOutcomeRecord outcome;

    /**
     * Constructor for creating an appointment
     * @param id the ID of the appointment
     * @param patientId the ID of the patient
     * @param doctorId the ID of the doctor
     * @param date the date of the appointment
     * @param timeslot the timeslot of the appointment
     * @param status the status of the appointment
     */

    public Appointment(int id, String patientId, String doctorId, String date, int timeslot, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
        this.status = status;
        this.outcome = null;
    }

    // Getters and Setters
    /**
     * Get the ID of the appointment
     * @return the ID of the appointment
     */
    public int getId() {
        return id;
    }
    /**
     * Get the ID of the appointment
     * @return the ID of the appointment
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Set the ID of the appointment
     * @param patientID the ID of the patient
     */
    public void setPatientId(String patientID) {
        this.patientId = patientID;
    }

    /**
     * Get the ID of the doctor
     * @return the ID of the doctor
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Set the ID of the doctor
     * @param doctorID the ID of the doctor
     */
    public void setDoctorId(String doctorID) {
        this.doctorId = doctorID;
    }

    /**
     * Get the date of the appointment
     * @return the date of the appointment
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the appointment
     * @param date the date of the appointment
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the timeslot of the appointment
     * @return the timeslot of the appointment
     */
    public int getTimeslot() {
        return timeslot;
    }

    /**
     * Set the timeslot of the appointment
     * @param timeslot the timeslot of the appointment
     */
    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Get the status of the appointment
     * @return the status of the appointment
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the appointment
     * @param status the status of the appointment
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get the outcome of the appointment
     * @return the outcome of the appointment
     */
    public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
        return outcome;
    }

    /**
     * Set the outcome of the appointment
     * @param outcome the outcome of the appointment
     */
    public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord outcome) {
        this.outcome = outcome;
    }

    /**
     * Get the outcome of the appointment
     * @return the outcome of the appointment
     */
    @Override
    public String toString() {
        return "ID: " + id +
                "\n - Patient ID: " + patientId +
                "\n - Doctor ID: " + doctorId +
                "\n - Date: " + date +
                "\n - Timeslot: " + TimeslotToInt.timeslotToString(timeslot) +
                "\n - Status: '" + status + '\'';
    }

    /**
     * Reschedule an appointment
     * @param doctorId the ID of the doctor
     * @param date the date of the appointment
     * @param timeslot the timeslot of the appointment
     */
    public void reschedule(String doctorId, String date, int timeslot) {
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
        this.status = "REQUESTED";
    }
}
