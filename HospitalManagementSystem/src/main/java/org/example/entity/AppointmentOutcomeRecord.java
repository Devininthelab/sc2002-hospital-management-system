package org.example.entity;

import java.time.LocalDate;
import java.util.*;


public class AppointmentOutcomeRecord {
    public enum presStatus {
        PENDING, DISPENSED;
    }

    private int appointmentId;
    private int timeslot;
    private String date;
    private ArrayList<String> typeOfService;
    private HashMap<String, List<Object>> prescription; // medication name -> List containing quantity and status
    private String consultationNotes;

    //private boolean followUpAction; what's this for

    /*public AppointmentOutcomeRecord(int appointmentId, String date, int timeslot, String consultationNotes, Prescription prescription) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.timeslot = timeslot;
        this.prescribedMedication = new HashMap<>(prescription.getMedications());
        this.consultationNotes = consultationNotes;
        this.typeOfService = new ArrayList<>();
    }*/
    // Constructor for initializing a new appointment (without prescription data)
    public AppointmentOutcomeRecord(int appointmentId, String date, int timeslot) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.timeslot = timeslot;
        this.prescription = new HashMap<>();
        this.consultationNotes = "";
        this.typeOfService = new ArrayList<>();
    }

    // Constructor for retrieving appointment data from a database (CSV)
    public AppointmentOutcomeRecord(int appointmentId, String date, int timeslot, String consultationNotes,
                                    HashMap<String, List<Object>> prescription, ArrayList<String> typeOfService) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.timeslot = timeslot;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
        this.typeOfService = typeOfService;
    }

    // Getter for prescription
    public HashMap<String, List<Object>> getPrescription() {
        return prescription;
    }

    // Setter for prescription
    public void setPrescription(HashMap<String, List<Object>> prescription) {
        this.prescription = prescription;
    }

    // Add type of service for the appointment
    public void addTypeOfService(String typeOfService) {
        this.typeOfService.add(typeOfService);
    }

    // Remove type of service from the appointment
    public void deleteTypeOfService(String typeOfService) {
        this.typeOfService.remove(typeOfService);
    }

    // Getter for typeOfService
    public ArrayList<String> getTypeOfService() {
        return typeOfService;
    }

    // Setter for typeOfService
    public void setTypeOfService(ArrayList<String> typeOfService) {
        this.typeOfService = typeOfService;
    }

    // Getter for appointmentId
    public int getAppointmentId() {
        return appointmentId;
    }

    // Setter for appointmentId
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    // Getter for timeslot
    public int getTimeslot() {
        return timeslot;
    }

    // Setter for timeslot
    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    // Getter for date
    public String getDate() {
        return date;
    }

    // Setter for date
    public void setDate(String date) {
        this.date = date;
    }

    // Getter for consultationNotes
    public String getConsultationNotes() {
        return consultationNotes;
    }

    // Setter for consultationNotes
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    // for pharmacist
    public boolean updateMedicationStatus(String medication, presStatus status) {
        if (prescription.containsKey(medication)) {
            List<Object> details = prescription.get(medication);
            details.set(1, status.toString().toLowerCase()); // Update the status part of the prescription
            return true;
        }
        return false;
    }

    /*public void updateMedicationStatusFromInput() { //Update status of Medicine_List.csv
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the index of the Medicine_List.csv status to update (0 to " + (medicationStatus.size() - 1) + "): ");

        int index = scanner.nextInt(); // Read index
        scanner.nextLine(); // Consume the newline

        if (index >= 0 && index < medicationStatus.size()) {
            System.out.print("Enter the new Medicine_List.csv status: ");
            String newStatus = scanner.nextLine(); // Read new status
            medicationStatus.set(index, newStatus); // Update the status
            System.out.println("Medication status updated to: " + newStatus);
        } else {
            System.out.println("Invalid index! Please provide a valid index.");
        }
    }
     */
    @Override
    public String toString() {
        return "AppointmentOutcomeRecord{" +
                "appointmentId=" + appointmentId +
                ", date='" + date + '\'' +
                ", timeslot=" + timeslot +
                ", consultationNotes='" + consultationNotes + '\'' +
                ", typeOfService=" + typeOfService +
                ", prescription=" + prescription +
                '}';
    }
}
