package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private static int idCounter = 0;
    enum Gender {
        MALE, FEMALE
    }

    enum BloodType {
        Apos, Aneg,
        Bpos, Bneg,
        ABpos, ABneg,
        Opos, Oneg
    }

    private int id;
    private String name;
    private int dateOfBirth;
    private Gender gender;
    private BloodType bloodType;
    // patient hold list of pending appointment and list of completed outcome record
    private List<Appointment> appointments;
    private List<AppointmentOutcomeRecord> outcomeRecords;
    // medical records and outcome record merged in 1


    public Patient(String name, int dateOfBirth, Gender gender, BloodType bloodType) {
        this.id = idCounter++;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        appointments = new ArrayList<>();
        outcomeRecords = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String toString() {
        return "Patient [id=" + id + ", name=" + name + "]";
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addOutcomeRecord(AppointmentOutcomeRecord outcomeRecord) {
        outcomeRecords.add(outcomeRecord);
    }

    // consider moving this to a control appointment manager - single responsibility principle
    public void scheduleAppointment(int doctorId, int timeslot) {

    }

    public void reScheduleAppointment(int doctorId, int timeslot) {

    }

    public void cancelAppointment(int doctorId, int timeslot) {

    }
}
