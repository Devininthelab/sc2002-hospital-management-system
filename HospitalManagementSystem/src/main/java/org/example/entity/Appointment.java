package org.example.entity;

import java.time.LocalDate;

public class Appointment {
    // might need to store this somewhere to get persistence
    private static int counter = 0;

    private int id;
    private int doctorId;
    private int patientId;
    private LocalDate date;
    private int timeslot;
    private String status;

    public Appointment(int doctorId, int timeslot, LocalDate date) {
        id = counter++;
        this.doctorId = doctorId;
        this.patientId = doctorId;
        this.timeslot = timeslot;
        this.date = date;
        status = "pending";
    }

    public void reSchedule(int doctorId, int timeslot) {
        this.doctorId = doctorId;
        this.timeslot = timeslot;
    }

    // Parameterized constructor
    public Appointment(int appointmentID, int patientId, int doctorId, LocalDate date,
                      int timeslot, String status) {
        this.id = counter++;
        this.patientId = patientId;
        this.doctorId = doctorId;
        //this.date = date; // Again, consider using a date type
        this.timeslot = timeslot;
        this.status = status; // Status can be customized during object creation
    }

    // Getters and Setters

    public int getPatientID() {
        return patientId;
    }

    public void setPatientID(int patientID) {
        this.patientId = patientID;
    }

    public int getDoctorID() {
        return doctorId;
    }

    public void setDoctorID(int doctorID) {
        this.doctorId = doctorID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + id +
                ", patientID=" + patientId +
                ", doctorID=" + doctorId +
                ", date=" + //date +
                ", timeslot=" + timeslot +
                ", status='" + status + '\'' +
                '}';
    }
    
    // Method to create an AppointmentOutcomeRecord if status is completed
    public AppointmentOutcomeRecord createOutcomeRecordIfCompleted() {
        if (status.equalsIgnoreCase("completed")) {
			AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(id, date, ""); //TODO how to add parameters.
            System.out.println("Outcome record created: " + outcomeRecord);
            return outcomeRecord;
        } else {
            System.out.println("Appointment is not completed. No outcome record created.");
            return null;
        }
    }


}
