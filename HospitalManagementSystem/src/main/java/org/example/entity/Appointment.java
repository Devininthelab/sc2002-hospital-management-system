package org.example.entity;

public class Appointment {
    // might need to store this somewhere to get persistence
    private static int counter = 0;

    private int id;
    private int doctorId;
    private int timeslot;
    private String status;

    public Appointment(int doctorId, int timeslot) {
        id = counter++;
        this.doctorId = doctorId;
        this.timeslot = timeslot;
        status = "pending";
    }

    public void reSchedule(int doctorId, int timeslot) {
        this.doctorId = doctorId;
        this.timeslot = timeslot;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // Default constructor
    public Appointment() {
        this.appointmentID = 0;
        this.patientID = 0;
        this.doctorID = 0;
        this.date = 0; // You might want to use a date type
        this.timeslot = 0;
        this.status = "Pending"; // Default status
    }

    // Parameterized constructor
    public Appointment(int appointmentID, int patientID, int doctorID, int date,
                      int timeslot, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date; // Again, consider using a date type
        this.timeslot = timeslot;
        this.status = status; // Status can be customized during object creation
    }

    // Getters and Setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getDate() {
        return date; 
    }

    public void setDate(int date) {
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
                "appointmentID=" + appointmentID +
                ", patientID=" + patientID +
                ", doctorID=" + doctorID +
                ", date=" + date +
                ", timeslot=" + timeslot +
                ", status='" + status + '\'' +
                '}';
    }
    
    // Method to create an AppointmentOutcomeRecord if status is completed
    public AppointmentOutcomeRecord createOutcomeRecordIfCompleted() {
        if (status.equalsIgnoreCase("completed")) {
			AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(); //TODO how to add parameters.
            System.out.println("Outcome record created: " + outcomeRecord);
            return outcomeRecord;
        } else {
            System.out.println("Appointment is not completed. No outcome record created.");
            return null;
        }
    }


}
