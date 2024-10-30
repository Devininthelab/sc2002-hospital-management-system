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
}
