package org.example.menu;

import org.example.entity.Administrator;
import org.example.repository.AppointmentRepository;
import org.example.repository.MedicineRepository;
import org.example.repository.StaffRepository;
import org.example.entity.Staff;

import java.util.Scanner;

public class AdministratorMenu implements Menu {
    private final StaffRepository staffRepository = new StaffRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final MedicineRepository medicineRepository = new MedicineRepository();
    private Administrator administrator; // current addministrator interacting with the menu
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
     * Log in to the system, a patient should enter their id and password UNTIL they are correct
     */
    public void login() {
        while (true) {
            System.out.print("Please enter your administrator id: ");
            String id = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();
            administrator = staffRepository.getAdministratorById(id);
            if (administrator == null) {
                System.out.println("Administrator not found. Try again");
                continue;
            }

            if (administrator.getPassword().equals(password)) {
                System.out.println("You are logged in.");
                break;
            }

            System.out.println("Wrong password. Try again");
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
    }


    /**
     * Remove a staff member from the system
     */
    public void removeStaff() {

    }

    /**
     * View the list of staff members
     */
    public void viewStaffList() {

    }


    /**
     * View the list of scheduled appointments
     */
    public void viewScheduledAppointments() {

    }

    /**
     * Update the status of an appointment
     */
    public void updateAppointmentStatus() {

    }

    /**
     * Record the outcome of an appointment
     */
    public void recordAppointmentOutcome() {

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
