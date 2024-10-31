package org.example.entity;

import java.time.LocalDate;
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

    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;
    // patient hold list of pending appointment and list of completed outcome record
    // medical records and outcome record merged in 1


    public Patient(int id, String name, LocalDate dateOfBirth, Gender gender, BloodType bloodType) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
    }

    // get to send read query to database
    public int getId() {
        return id;
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
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


}
