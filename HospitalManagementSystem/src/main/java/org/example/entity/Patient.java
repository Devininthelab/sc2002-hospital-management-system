package org.example.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String id;
    private String password;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String contact;
    private String bloodType;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> prescriptions;

    public Patient(String id, String password, String name, LocalDate dateOfBirth,
                   String gender, String contact, String bloodType) {
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

    }


    public String getId() { return id; }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    // set to send update query to database
    private void setId(String id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
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
                + "\nBlood type: " + bloodType
                + diagnoses_treatments;
    }

    public String toString() {
        return "Patient [id=" + id + ", name=" + name + "]";
    }


}
