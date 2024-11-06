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
    private LocalDate date;
    private int timeslot;
    private Status status;
    private Prescription prescription;

    public Appointment(String doctorId, int timeslot, LocalDate date) {
        this.id = counter++;
        this.doctorId = doctorId;
        this.timeslot = timeslot;
        this.date = date;
        this.status = Status.PENDING;
    }

    public void reSchedule(String doctorId, int timeslot) {
        this.doctorId = doctorId;
        this.timeslot = timeslot;
    }

    // Parameterized constructor
    public Appointment(int appointmentID, String patientId, String doctorId, LocalDate date,
                      int timeslot, Status status) {
        this.id = counter++;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date; // Again, consider using a date type
        this.timeslot = timeslot;
        this.status = status; // Status can be customized during object creation
        this.prescription = new Prescription(id, patientId, doctorId);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Prescription getPrescription() {
        return prescription;
    }
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
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
			AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(id, date, "", prescription); //TODO how to add parameters.
            System.out.println("Outcome record created: " + outcomeRecord);
            return outcomeRecord;
        } else {
            System.out.println("Appointment is not completed. No outcome record created.");
            return null;
        }
    }


}
