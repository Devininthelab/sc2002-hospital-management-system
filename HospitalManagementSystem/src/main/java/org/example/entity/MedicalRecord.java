package org.example.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicalRecord {


    public MedicalRecord(String username, String password, String contact, Date dateOfBirth, String gender, String bloodType) {
        super(username, password, "Patient", contact);  // Role is set as "Patient"
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    // Getters and Setters
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public List<String> getDiagnoses() { return diagnoses; }
    public List<String> getTreatments() { return treatments; }

    public void addDiagnosis(String diagnosis) {
        this.diagnoses.add(diagnosis);
    }

    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
    }

    @Override
    public String viewProfile() {
        return super.viewProfile() + "\nDate of Birth: " + dateOfBirth + "\nGender: " + gender + "\nBlood Type: " + bloodType;
    }
}