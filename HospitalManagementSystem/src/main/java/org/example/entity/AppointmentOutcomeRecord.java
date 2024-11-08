package org.example.entity;

import org.example.repository.MedicationRepository;

import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecord {
    private final MedicationRepository repo = new MedicationRepository();
    private int appointmentId;
    private int timeslot;
    private String date;
    private ArrayList<String> typeOfService;
    private String consultationNotes;
    private List<Medication> medications;

    // Constructor to initialize from a repository
    public AppointmentOutcomeRecord(int appointmentId, String date, int timeslot) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.timeslot = timeslot;
        this.typeOfService = new ArrayList<>();
        this.consultationNotes = "";
        repo.loadMedicationsFromCSV();
        this.medications = repo.getMedicationsById(appointmentId);
    }

    public AppointmentOutcomeRecord(int appointmentId, String date, int timeslot, String consultationNotes) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.timeslot = timeslot;
        this.consultationNotes = consultationNotes;
        this.typeOfService = new ArrayList<>();
        repo.loadMedicationsFromCSV();
        this.medications = repo.getMedicationsById(appointmentId);
    }

    public AppointmentOutcomeRecord(int appointmentId, String date, int timeslot, String consultationNotes, ArrayList<String> typeOfService) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.timeslot = timeslot;
        this.consultationNotes = consultationNotes;
        this.typeOfService = typeOfService;
        repo.loadMedicationsFromCSV();
        this.medications = repo.getMedicationsById(appointmentId);
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void addMedication(Medication medication) {
        this.medications.add(medication);
    }

    public void addTypeOfService(String typeOfService) {
        this.typeOfService.add(typeOfService);
    }

    public void deleteTypeOfService(String typeOfService) {
        this.typeOfService.remove(typeOfService);
    }

    public ArrayList<String> getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(ArrayList<String> typeOfService) {
        this.typeOfService = typeOfService;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public boolean updateMedicationStatus(String medicationName, Medication.Status status) {
        for (Medication medication : medications) {
            if (medication.getName().equals(medicationName)) {
                medication.setStatus(Medication.Status.valueOf(status.name()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AppointmentOutcomeRecord{" +
                "appointmentId=" + appointmentId +
                ", date='" + date + '\'' +
                ", timeslot=" + timeslot +
                ", consultationNotes='" + consultationNotes + '\'' +
                ", typeOfService=" + typeOfService +
                ", Prescription:" +
                " medications=" + medications +
                '}';
    }
}