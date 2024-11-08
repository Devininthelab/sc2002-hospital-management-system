package org.example.entity;

import java.time.LocalDate;

public class Appointment {
    // might need to store this somewhere to get persistence
    private static int counter = 0;
    public enum Status {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }
    private int id;
    private String doctorId;
    private String patientId;
    private String date;
    private int timeslot; // 8 hours a day, 6 days a week = 8 x 6 = 48 timeslots ranging from 0 to 47
    private Status status;
    private AppointmentOutcomeRecord outcome = null;
    //AVOID DUPLICATE ID WHEN WORKING WITH DATABASE AND COUNTER
    public static void setCounter(int highestId) {
        counter = highestId + 1;
    }
    // Constructor for creating an appointment (patient perspective)
    public Appointment(String patientId, String doctorId, String date, int timeslot) {
        this.id = counter++;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
        this.status = Status.PENDING;
    }

    public void reSchedule(String doctorId, int timeslot) {
        this.doctorId = doctorId;
        this.timeslot = timeslot;
    }

    // Parameterized constructor
    // Constructor when retrieving from database
    public Appointment(int id, String patientId, String doctorId, String date, int timeslot, Status status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
        this.status = status;
        //this.prescription = new Prescription(id, patientId, doctorId)
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getPatientID() {
        return patientId;
    }

    public void setPatientID(String patientID) {
        this.patientId = patientID;
    }

    public String getDoctorID() {
        return doctorId;
    }

    public void setDoctorID(String doctorID) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
        if (status == Status.COMPLETED) {
			AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(id, date, timeslot);
            this.outcome = outcomeRecord;
            //TODO how to add parameters.
            System.out.println("Outcome record created: " + outcomeRecord);
            return outcomeRecord;
        } else {
            System.out.println("Appointment is not completed. No outcome record created.");
            return null;
        }
    }
}
