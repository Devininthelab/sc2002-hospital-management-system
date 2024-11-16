package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;

import java.util.List;
import java.util.Scanner;

public class AdministratorMenu implements Menu {
    private StaffRepository staffRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRepository;
    private PrescriptionRepository prescriptionRepository;
    private MedicineRepository medicineRepository;
    private MedicineRequestRepository medicineRequestRepository;
    private Administrator administrator;
    private Scanner scanner;

    public AdministratorMenu(StaffRepository staffRepository, AppointmentRepository appointmentRepository,
                             AppointmentOutcomeRecordRepository appointmentOutcomeRepository,
                             PrescriptionRepository prescriptionRepository, MedicineRepository medicineRepository,
                             MedicineRequestRepository medicineRequestRepository, Scanner scanner) {
        this.staffRepository = staffRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentOutcomeRepository = appointmentOutcomeRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicineRepository = medicineRepository;
        this.medicineRequestRepository = medicineRequestRepository;
        this.scanner = scanner;
    }

    /**
     * Start the Administrator menu, which requires the administrator to log in first
     */
    public void start() {
        login();
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = Integer.valueOf(scanner.nextLine());
            handleChoice(choice);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        } while (choice != 16);  // Exit when logout is chosen
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
                "7. View Medicine Inventory Stock Levels\n" +
                "8. View Appointment Outcome Record\n" +
                "9. Add Medication to Inventory\n" +
                "10. Update Inventory Stock Levels\n" +
                "11. Remove Medication from Inventory\n" +
                "12. Set Low Stock Alert Level\n" +
                "13. View Low Stock Inventory Items\n" +
                "14. Show all Replenishment Requests\n" +
                "15. Approve Replenishment Request\n" +
                "16. Logout");
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
                viewMedicines();
                break;
            case 8:
                viewAppointmentOutcomeRecord();
                break;
            case 9:
                addMedicineToInventory();
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
                showAllReplenishmentRequests();
                break;
            case 15:
                approveReplenishmentRequest();
                break;
            case 16:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    // update appointment status is redundant (6)
    // merge 8 and 5
    // leave out 6 and 7
    // 10 give upper bound for quantity
    // Done logic: 1, 2, 3, 4, 5, 7, 8, 9, 10



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
        int age = Integer.valueOf(scanner.nextLine());

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
        int id = Integer.valueOf(scanner.nextLine());
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

//    /**
//     * Record the outcome of an appointment
//     * NEED TO CONFIRM THE LOGIC
//     */
//    public void recordAppointmentOutcome() {
//        System.out.println("Enter the appointment id: ");
//        int id = scanner.nextInt();
//        scanner.nextLine(); // remove input buffer
//        Appointment appointment = appointmentRepository.getAppointmentById(id);
//        // validation check for appointment: If that appointment exists
//        if (appointment == null) {
//            System.out.println("Appointment not found.");
//        }
//        else {
//            System.out.println("Enter the date of the appointment outcome(DD-MM-YYYY): ");
//            String date = scanner.nextLine();
//            System.out.println("Enter consultation notes: ");
//            String consultationNotes = scanner.nextLine();
//
//            // Input Services used
//            List<String> typeOfService = new ArrayList<>();
//            while (true) {
//                System.out.println("Enter the type of service (Enter -1 to stop): ");
//                String input = scanner.nextLine();
//                if (input.equals("-1")) {
//                    break;
//                }
//                typeOfService.add(input);
//            }
//
//            // Input prescriptions
//            List<Prescription> prescriptions = new ArrayList<>();
//            while (true) {
//                System.out.println("Enter the medication name (Enter -1 to stop): ");
//                String medicationName = scanner.nextLine();
//                if (medicationName.equals("-1")) {
//                    break;
//                }
//                System.out.println("Enter the dosage: ");
//                int dosage = scanner.nextInt();
//                scanner.nextLine(); // remove input buffer
//                System.out.println("Enter the status: ");
//                String status = scanner.nextLine();
//                Prescription prescription = new Prescription(id, medicationName, dosage, status);
//                prescriptions.add(prescription);
//            }
//
//            AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(id, date, consultationNotes, typeOfService, prescriptions);
//            appointmentOutcomeRepository.addAppointmentOutcomeRecord(record);
//            prescriptionRepository.addPrescriptions(prescriptions);
//        }
//    }


    /**
     * View the outcome record of an appointment
     */
    public void viewAppointmentOutcomeRecord() {
        System.out.println("Enter the appointment id: ");
        int id = Integer.valueOf(scanner.nextLine());
        AppointmentOutcomeRecord record = appointmentOutcomeRepository.getAppointmentOutcomeRecordById(id);
        if (record == null) {
            System.out.println("Appointment outcome record not found.");
        }
        else {
            System.out.println(record.toString());
        }
    }

    /**
     * Add a new medicine to the inventory
     */
    public void addMedicineToInventory() {
        System.out.println("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        System.out.println("Enter current available stock: ");
        int stockLevel = Integer.valueOf(scanner.nextLine());
        System.out.println("Enter low threshold of the medicine: ");
        int lowThreshold = Integer.valueOf(scanner.nextLine());
        System.out.println("Enter high threshold of the medicine: ");
        int highThreshold = Integer.valueOf(scanner.nextLine());
        Medicine medicine = new Medicine(medicineName, stockLevel, lowThreshold, highThreshold);
        medicineRepository.addMedicine(medicine);
    }

    /**
     * View All Medicines in Inventory
     */
    public void viewMedicines() {
        List<Medicine> medicines = medicineRepository.getAllMedicines();
        if (medicines == null || medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
        } else {
            System.out.println("Inventory Stock Levels:");
            for (Medicine medicine : medicines) {
                System.out.println(medicine.toString());
            }
        }
    }

    /**
     * Update the stock levels of a medication in the inventory
     */
    public void updateInventoryStockLevels() {
        System.out.println("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        if(!medicineRepository.medicineExists(medicineName)) {
            System.out.println("Medicine not found. Please try again.");
        }
        else {
            System.out.println("Enter new quantity for " +  medicineName + ": ");
            int quantity = Integer.valueOf(scanner.nextLine());
            medicineRepository.updateStockLevel(medicineName, quantity);
        }
    }

    /**
     * Remove a medication from the inventory
     */
    public void removeMedicationFromInventory() {
        System.out.println("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        if(!medicineRepository.medicineExists(medicineName)) {
            System.out.println("Medicine not found. Please try again.");
        }
        else {
            medicineRepository.removeMedicine(medicineName);
            System.out.println("Medicine " + medicineName + " removed from inventory.");
        }
    }


    /**
     * Set the low stock alert level for a medication
     */
    public void setLowStockAlertLevel() {
        System.out.println("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        if(!medicineRepository.medicineExists(medicineName)) {
            System.out.println("Medicine not found. Please try again.");
        }
        else {
            System.out.println("Enter the new low stock alert level: ");
            int lowThreshold = Integer.valueOf(scanner.nextLine());
            medicineRepository.updateLowThreshold(medicineName, lowThreshold);
        }
    }


    /**
     * View the list of medications with low stock
     */
    public void viewLowStockInventoryItems() {
        List<Medicine> lowStockMedicines = medicineRepository.getLowStockMedicines();
        if (lowStockMedicines == null || lowStockMedicines.isEmpty()) {
            System.out.println("No low stock items.");
        } else {
            System.out.println("Low Stock Inventory Items:");
            for (Medicine medicine : lowStockMedicines) {
                System.out.println(medicine.toString());
            }
        }
    }

    /**
     * Show all replenishment requests for medications
     */
    public void showAllReplenishmentRequests() {
        List<MedicineRequest> requests = medicineRequestRepository.getAllMedicineRequests();
        if (requests == null || requests.isEmpty()) {
            System.out.println("No replenishment requests.");
        } else {
            System.out.println("Replenishment Requests:");
            for (MedicineRequest request : requests) {
                System.out.println(request.toString());
            }
        }
    }


    /**
     * Approve a replenishment request for a medication
     */
    public void approveReplenishmentRequest() {
        System.out.println("Enter the request id: ");
        int id = Integer.valueOf(scanner.nextLine());
        MedicineRequest request = medicineRequestRepository.getMedicineRequestById(id);
        if (request == null) {
            System.out.println("Request not found.");
        }
        else {
            List<String> medicineNames = request.getMedicines();
            medicineRequestRepository.approveMedicineRequest(id);
            // update in Medicine List
            for (String medicineName : medicineNames) {
                Medicine medicine = medicineRepository.getMedicine(medicineName);
                if (medicine != null) {
                    medicineRepository.updateStockLevel(medicineName, medicine.getHighThreshold());
                }
            }
            System.out.println("Request approved.");
        }
    }

}
