package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdministratorMenu implements Menu {
    private final StaffRepository staffRepository = new StaffRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    //private final MedicineRepository medicineRepository = new MedicineRepository();
    private final AppointmentOutcomeRecordRepository appointmentOutcomeRepository = new AppointmentOutcomeRecordRepository();
    private final PrescriptionRepository prescriptionRepository = new PrescriptionRepository();
    private Administrator administrator;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Start the Administrator menu, which requires the administrator to log in first
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        login();
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            handleChoice(choice);
        } while (choice != 9);  // Exit when logout is chosen
    }

    /**
     * Log in to the system, an administrator should enter his id and password UNTIL they are correct
     */
    public void login() {
        while (true) {
            System.out.print("Please enter your administrator id: ");
            String id = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();
            administrator = staffRepository.getAdministratorByCredentials(id, password);
            if (administrator == null) {
                System.out.println("Incorrect id or password. Please try again.");
                continue;
            }
            System.out.println("Welcome, " + administrator.getName() + "!");
            break;
        }
    }

    /**
     * Display the Administrator menu, showing the available options
     */
    public void displayMenu() {
        System.out.println("=====Administrator MENU=====");
        System.out.println("1. Add Staff Member\n" +
                "2. Update Staff Information\n" +
                "3. Remove Staff Member\n" +
                "4. View Staff List\n" +
                "5. View Scheduled Appointments\n" +
                "6. Update Appointment Status\n" +
                "7. Record Appointment Outcome\n" +
                "8. View Appointment Outcome Record\n" +
                "9. Add Medication to Inventory\n" +
                "10. Update Inventory Stock Levels\n" +
                "11. Remove Medication from Inventory\n" +
                "12. Set Low Stock Alert Level\n" +
                "13. View Low Stock Inventory Items\n" +
                "14. Approve Replenishment Request\n" +
                "15. Logout");
    }



    /**
     * Handle the Administrator's choice from the menu
     * @param choice the Administrator's choice
     */
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                addStaff();
                break;
            case 2:
                updateStaff();
                break;
            case 3:
                removeStaff();
                break;
            case 4:
                viewStaffList();
                break;
            case 5:
                viewScheduledAppointments();
                break;
            case 6:
                updateAppointmentStatus();
                break;
            case 7:
                recordAppointmentOutcome();
                break;
            case 8:
                viewAppointmentOutcomeRecord();
                break;
            case 9:
                addMedicationToInventory();
                break;
            case 10:
                updateInventoryStockLevels();
                break;
            case 11:
                removeMedicationFromInventory();
                break;
            case 12:
                setLowStockAlertLevel();
                break;
            case 13:
                viewLowStockInventoryItems();
                break;
            case 14:
                approveReplenishmentRequest();
                break;
            case 15:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    /**
     * Add a new staff member to the system
     */
    public void addStaff(){
        System.out.println("Enter the staff member's id: ");
        String id = scanner.nextLine();
        System.out.println("Enter the staff member's name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the staff member's password: ");
        String password = scanner.nextLine();
        System.out.println("Enter the staff member's role: ");
        String role = scanner.nextLine();
        System.out.println("Enter the staff member's gender: ");
        String gender = scanner.nextLine();
        System.out.println("Enter the staff member's age: ");
        int age = scanner.nextInt();

        Staff staff = new Staff(id, name, role, gender, age, password);
        staffRepository.addStaffRepo(staff);
    }

    /**
     * Update an existing staff member's information
     */
    public void updateStaff() {
        System.out.println("Enter the staff member's id: ");
        String id = scanner.nextLine();
        System.out.println("Enter the field to be updated (name, role, password, gender, age): ");
        String field = scanner.nextLine();
        System.out.println("Enter the new value: ");
        String newValue = scanner.nextLine();
        staffRepository.updateStaffRepo(id, field, newValue);
    }


    /**
     * Remove a staff member from the system
     */
    public void removeStaff() {
        System.out.println("Enter the staff member's id: ");
        String id = scanner.nextLine();
        if (id.equals(administrator.getId())) {
            System.out.println("You cannot remove yourself.");
            return;
        }
        staffRepository.removeStaffRepo(id);
    }

    /**
     * View the list of staff members
     */
    public void viewStaffList() {
        List<Staff> staffList = staffRepository.getAllStaffs();
        if (staffList == null || staffList.isEmpty()) {
            System.out.println("No staff members.");
        } else {
            System.out.println("Staff Members:");
            for (Staff staff : staffList) {
                System.out.println(staff.toString());
            }
        }
    }


    /**
     * View the list of scheduled appointments of all patients
     * Display the details and status of each appointment
     */
    public void viewScheduledAppointments() {
        List<Appointment> appointments = appointmentRepository.getAllAppointments();
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
        } else {
            System.out.println("Scheduled Appointments:");
            for(Appointment appointment : appointments) {
                System.out.println(appointment.toString());
            }
        }
    }


    /**
     * Update the status of an appointment
     */
    public void updateAppointmentStatus() {
        System.out.println("Enter the appointment id: ");
        int id = scanner.nextInt();
        System.out.println("Enter the new status: ");
        String status = scanner.nextLine();
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        if (appointment == null) {
            System.out.println("Appointment not found.");
        }
        else {
            appointmentRepository.updateAppointmentStatus(id, status);
        }
    }

    /**
     * Record the outcome of an appointment
     */
    public void recordAppointmentOutcome() {
        System.out.println("Enter the appointment id: ");
        int id = scanner.nextInt();
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        // validation check for appointment: If that appointment exists
        if (appointment == null) {
            System.out.println("Appointment not found.");
        }
        else {
            System.out.println("Enter the date of the appointment outcome(DD-MM-YYYY): ");
            String date = scanner.nextLine();
            System.out.println("Enter consultation notes: ");
            String consultationNotes = scanner.nextLine();

            // Input Services used
            List<String> typeOfService = new ArrayList<>();
            while (true) {
                System.out.println("Enter the type of service (Enter -1 to stop): ");
                String input = scanner.nextLine();
                if (input.equals("-1")) {
                    break;
                }
                typeOfService.add(input);
            }

            // Input prescriptions
            List<Prescription> prescriptions = new ArrayList<>();
            while (true) {
                System.out.println("Enter the medication name (Enter -1 to stop): ");
                String medicationName = scanner.nextLine();
                if (medicationName.equals("-1")) {
                    break;
                }
                System.out.println("Enter the dosage: ");
                int dosage = scanner.nextInt();
                scanner.nextLine(); // remove input buffer
                System.out.println("Enter the status: ");
                String status = scanner.nextLine();
                Prescription prescription = new Prescription(id, medicationName, dosage, status);
                prescriptions.add(prescription);
            }

            AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(id, date, consultationNotes, typeOfService, prescriptions);
            appointmentOutcomeRepository.saveRecordsToCSV(record);
            prescriptionRepository.savePrescriptionsToCSV(prescriptions);

        }
    }


    /**
     * View the outcome record of an appointment
     */
    public void viewAppointmentOutcomeRecord() {

    }

    /**
     * Add a new medication to the inventory
     */
    public void addMedicationToInventory() {

    }

    /**
     * Update the stock levels of a medication in the inventory
     */
    public void updateInventoryStockLevels() {

    }

    /**
     * Remove a medication from the inventory
     */
    public void removeMedicationFromInventory() {

    }


    /**
     * Set the low stock alert level for a medication
     */
    public void setLowStockAlertLevel() {

    }


    /**
     * View the list of medications with low stock
     */
    public void viewLowStockInventoryItems() {

    }


    /**
     * Approve a replenishment request for a medication
     */
    public void approveReplenishmentRequest() {

    }



}
