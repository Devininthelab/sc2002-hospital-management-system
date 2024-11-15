package org.example.entity;

import java.time.LocalDate;

/**
 * Appointment
 * Depend on
 * - AppointmentOutcomeRecord
 */

/**4 STATUS: ACCEPTED, REQUESTED, REJECTED, COMPLETED*/
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
     * @param id
     * @param patientId
     * @param doctorId
     * @param date
     * @param timeslot
     * @param status
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
    public int getId() {
        return id;
    }
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientID) {
        this.patientId = patientID;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorID) {
        this.doctorId = doctorID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
        return outcome;
    }
    public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "Appointment" + id +
                "\n - Patient ID: " + patientId +
                "\n - Doctor ID: " + doctorId +
                "\n - Date: " + //date +
                "\n - Timeslot: " + timeslot +
                "\n - Status: '" + status + '\'';
    }

    /**
     * Reschedule an appointment
     * @param doctorId
     * @param date
     * @param timeslot
     */
    public void reschedule(String doctorId, String date, int timeslot) {
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
    }
}
