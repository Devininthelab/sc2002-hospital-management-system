package org.example.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a patient with personal details, medical history,
 * and contact information.
 */
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

    /**
     * Constructor to create a new Patient object.
     *
     * @param id           the unique identifier of the patient
     * @param password     the patient's password
     * @param name         the name of the patient
     * @param dateOfBirth  the date of birth of the patient
     * @param gender       the gender of the patient
     * @param contact      the contact information of the patient
     * @param bloodType    the blood type of the patient
     */
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

    /**
     * Retrieves the patient's ID.
     *
     * @return the patient's ID
     */
    public String getId() { return id; }

    /**
     * Retrieves the patient's password.
     *
     * @return the patient's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the patient's name.
     *
     * @return the patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the patient's date of birth.
     *
     * @return the patient's date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Retrieves the patient's gender.
     *
     * @return the patient's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Retrieves the patient's blood type.
     *
     * @return the patient's blood type
     */
    public String getBloodType() {
        return bloodType;
    }


    /**
     * Sets the patient's ID (private for controlled updates).
     *
     * @param id the new ID for the patient
     */
    private void setId(String id) { this.id = id; }

    /**
     * Updates the patient's name.
     *
     * @param name the new name of the patient
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the patient's password.
     *
     * @param password the new password of the patient
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Updates the patient's date of birth.
     *
     * @param dateOfBirth the new date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Updates the patient's gender.
     *
     * @param gender the new gender of the patient
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Updates the patient's blood type.
     *
     * @param bloodType the new blood type of the patient
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Updates the patient's diagnoses.
     *
     * @param diagnoses the new list of diagnoses
     */
    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    /**
     * Updates the patient's treatments.
     *
     * @param treatments the new list of treatments
     */
    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    /**
     * Updates the patient's prescriptions.
     *
     * @param prescriptions the new list of prescriptions
     */
    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    /**
     * Adds a diagnosis to the patient's medical history.
     *
     * @param diagnose the diagnosis to add
     */
    public void addDiagnose(String diagnose) {
        this.diagnoses.add(diagnose);
    }
    /**
     * Adds a treatment to the patient's medical history.
     *
     * @param treatment the treatment to add
     */
    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
    }

    /**
     * Adds a prescription to the patient's prescription list.
     *
     * @param prescription the prescription to add
     */
    public void addPrescription(String prescription) {
        this.prescriptions.add(prescription);
    }

    /**
     * Retrieves the patient's full medical record as a formatted string.
     *
     * @return a formatted string of the patient's medical record
     */
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

    /**
     * Returns a string representation of the patient, including only ID and name.
     *
     * @return a string representation of the patient
     */
    public String toString() {
        return "Patient [id=" + id + ", name=" + name + "]";
    }

    /**
     * Retrieves the patient's contact information.
     *
     * @return the patient's contact information
     */
    public String getContact() {
        return contact;
    }

    /**
     * Retrieves the patient's list of diagnoses.
     *
     * @return the list of diagnoses
     */
    public List<String> getDiagnoses() {
        return diagnoses;
    }

    /**
     * Retrieves the patient's list of treatments.
     *
     * @return the list of treatments
     */
    public List<String> getTreatments() {
        return treatments;
    }

    /**
     * Retrieves the patient's list of prescriptions.
     *
     * @return the list of prescriptions
     */
    public List<String> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Updates the patient's contact information.
     *
     * @param contact the new contact information
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}
