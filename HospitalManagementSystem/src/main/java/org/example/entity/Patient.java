package org.example.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    public enum Gender {
        MALE, FEMALE
    }

    public enum BloodType {
        Apos, Aneg,
        Bpos, Bneg,
        ABpos, ABneg,
        Opos, Oneg
    }
    // implicit medical record
    private int id;
    private String password;
    private String name;
    private LocalDate dateOfBirth;
    private Patient.Gender gender;
    private String contact;
    private Patient.BloodType bloodType;
    private List<String> diagnoses;
    private List<String> treatments;

    // appointment management
    private List<Appointment> appointments;
    private List<AppointmentOutcomeRecord> appointmentOutcomes;
    // patient hold list of pending appointment and list of completed outcome record


    public Patient(int id, String password, String name, LocalDate dateOfBirth,
                   Gender gender, String contact, BloodType bloodType) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contact = contact;
        this.bloodType = bloodType;
        // to be populated when reading database
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.appointmentOutcomes = new ArrayList<>();
    }

    // get to send read query to database
    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    // set to send update query to database
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String medicalRecord() {
        String diagnoses_treatments = "Diagnoses: treatments\n";
        for (int i = 0; i < treatments.size(); i++) {
            diagnoses_treatments += diagnoses.get(i) + ": " + treatments.get(i) + "\n";
        }
        return "Patient id: " + id
                + "\nName: " + name
                + "\nDate of birth: " + dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\nGender: " + gender
                + "\nContact: " + contact
                + "\nBlood type: " + bloodType.name()
                + diagnoses_treatments;
    }

    public String toString() {
        return "Patient [id=" + id + ", name=" + name + "]";
    }


}
