package org.example.entity;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;


public class AppointmentOutcomeRecord {
private int patientID;
private ArrayList<String> prescribedMedication;
private ArrayList<String> medicationStatus; // New variable
private String consultationNotes;
private boolean followUpAction;
private int doctorID;
private int date;
private int timeslot;
private ArrayList<String> typeOfService;

// Default constructor
public AppointmentOutcomeRecord() {
    this.patientID = 0;
    this.prescribedMedication = new ArrayList<>();
    this.medicationStatus = new ArrayList<>(); 
    this.consultationNotes = "";
    this.followUpAction = false;
    this.doctorID = 0;
    this.date = 0; 
    this.timeslot = 0;
    this.typeOfService = new ArrayList<>();
}

// Parameterized constructor
public AppointmentOutcomeRecord(int patientID, ArrayList<String> prescribedMedication, ArrayList<String> medicationStatus,
                     String consultationNotes, boolean followUpAction, int doctorID,
                     int date, int timeslot, ArrayList<String> typeOfService) {
    this.patientID = patientID;
    this.prescribedMedication = prescribedMedication;
    this.medicationStatus = medicationStatus; // maybe use int, then 1,2,3 can indicate pending,rejected, approved.
    this.consultationNotes = consultationNotes;
    this.followUpAction = followUpAction;
    this.doctorID = doctorID;
    this.date = date;
    this.timeslot = timeslot;
    this.typeOfService = typeOfService;
}

// Getters and Setters
public int getPatientID() {
    return patientID;
}

public void setPatientID(int patientID) {
    this.patientID = patientID;
}

public ArrayList<String> getPrescribedMedication() {
    return prescribedMedication;
}

public void setPrescribedMedication(ArrayList<String> prescribedMedication) {
    this.prescribedMedication = prescribedMedication;
}

public ArrayList<String> getMedicationStatus() {
    return medicationStatus; // Updated return type
}

public void setMedicationStatus(ArrayList<String> medicationStatus) {
    this.medicationStatus = medicationStatus; // Updated parameter
}

public String getConsultationNotes() {
    return consultationNotes;
}

public void setConsultationNotes(String consultationNotes) {
    this.consultationNotes = consultationNotes;
}

public boolean isFollowUpAction() {
    return followUpAction;
}

public void setFollowUpAction(boolean followUpAction) {
    this.followUpAction = followUpAction;
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

public ArrayList<String> getTypeOfService() {
    return typeOfService;
}

public void setTypeOfService(ArrayList<String> typeOfService) {
    this.typeOfService = typeOfService;
}


public void updateMedicationStatusFromInput() { //Update status of medication.csv
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the index of the medication.csv status to update (0 to " + (medicationStatus.size() - 1) + "): ");
    
    int index = scanner.nextInt(); // Read index
    scanner.nextLine(); // Consume the newline

    if (index >= 0 && index < medicationStatus.size()) {
        System.out.print("Enter the new medication.csv status: ");
        String newStatus = scanner.nextLine(); // Read new status
        medicationStatus.set(index, newStatus); // Update the status
        System.out.println("Medication status updated to: " + newStatus);
    } else {
        System.out.println("Invalid index! Please provide a valid index.");
    }
}
@Override
public String toString() {
    return "AppointmentOutcomeRecord{" +
            "patientID=" + patientID +
            ", prescribedMedication=" + prescribedMedication +
            ", medicationStatus=" + medicationStatus + // Updated to include the new variable
            ", consultationNotes='" + consultationNotes + '\'' +
            ", followUpAction=" + followUpAction +
            ", doctorID=" + doctorID +
            ", date=" + date +
            ", timeslot=" + timeslot +
            ", typeOfService=" + typeOfService +
            '}';

}
 // Method for adding this record to the provided queue
    public void addToQueue(Queue<AppointmentOutcomeRecord> queue) {
        queue.add(this); // Add itself to the provided queue
        System.out.println("Record added to" + queue.getName() + "queue: " + this);
    }

// method for removing from queue
    public void setStatus(String newStatus, Queue<AppointmentOutcomeRecord> queue) {
        this.medicationStatus = newStatus; // Update the status // TODO - how to track status of different medications
        System.out.println("Status updated to: " + this.medicationStatus);

        // Check if the status is "completed"
        if (this.medicationStatus.equalsIgnoreCase("completed")) {
            queue.remove(this); // Remove from the queue
            System.out.println("Record removed from queue: " + this);
        }
    }

}
