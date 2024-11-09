package org.example.entity;

import java.time.LocalDate;

public class Appointment {
    // might need to store this somewhere to get persistence
    private int id;
    private String doctorId;
    private String patientId;
    private String date;
    private int timeslot; // 8 hours a day, 6 days a week = 8 x 6 = 48 timeslots ranging from 0 to 47
    private String status;
    private AppointmentOutcomeRecord outcome = null;
    //AVOID DUPLICATE ID WHEN WORKING WITH DATABASE AND COUNTER
    // Constructor for creating an appointment (patient perspective)
    public Appointment(int id, String patientId, String doctorId, String date, int timeslot) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
        this.status = "PENDING";
    }

    public void reschedule(String doctorId, String date, int timeslot) {
        this.doctorId = doctorId;
        this.date = date;
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
    //TODO: improve this
    public AppointmentOutcomeRecord createOutcomeRecordIfCompleted() {
        if (status == "COMPLETED") {
			AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(id, date, timeslot);
            this.outcome = outcomeRecord;
            System.out.println("Outcome record created: " + outcomeRecord);
            return outcomeRecord;
        } else {
            System.out.println("Appointment is not completed. No outcome record created.");
            return null;
        }
    }
}
