package org.example.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;


public class AppointmentOutcomeRecord {
    private int appointmentId;
    private LocalDate date;
    private ArrayList<String> typeOfService;
    private HashMap<String, String> prescribedMedication;
    private String consultationNotes;
    //private boolean followUpAction; what's this for

    public AppointmentOutcomeRecord(int appointmentId, LocalDate date, String consultationNotes) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.prescribedMedication = new HashMap<>();
        this.consultationNotes = consultationNotes;
        this.typeOfService = new ArrayList<>();
    }

    // For doctor
    public HashMap<String, String> getPrescribedMedication() {
        return prescribedMedication;
    }

    public void addTypeOfService(String typeOfService) {
        this.typeOfService.add(typeOfService);
    }

    public void deleteTypeOfService(String typeOfService) {
        this.typeOfService.remove(typeOfService);
    }

    // for pharmacist
    public boolean updateMedicationStatus(String medication, String status) {
        if (prescribedMedication.containsKey(medication)) {
            this.prescribedMedication.put(medication, status);
            return true;
        }
        return false;
    }


    public void updateMedicationStatusFromInput() { //Update status of Medicine_List.csv
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

}
