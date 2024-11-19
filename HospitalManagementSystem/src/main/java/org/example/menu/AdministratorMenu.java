package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;
import org.example.utils.ChangePage;
import org.example.utils.ColorText;

import java.util.List;
import java.util.Scanner;

/**
 * The AdministratorMenu class provides the menu-driven interface for an administrator
 * to manage staff, appointments, and inventory within the system.
 * Responsibilities include:
 * <ul>
 *     <li>Staff management: adding, updating, removing, and viewing staff members.</li>
 *     <li>Appointment management: viewing scheduled appointments and outcome records.</li>
 *     <li>Inventory management: managing medicines, stock levels, and replenishment requests.</li>
 * </ul>
 */
public class AdministratorMenu implements Menu {
    private StaffRepository staffRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRepository;
    private PrescriptionRepository prescriptionRepository;
    private MedicineRepository medicineRepository;
    private MedicineRequestRepository medicineRequestRepository;
    private Administrator administrator;
    private Scanner scanner;

    /**
     * Constructor to initialize the AdministratorMenu with the necessary repositories and scanner.
     *
     * @param staffRepository             the repository for managing staff data
     * @param appointmentRepository       the repository for managing appointment data
     * @param appointmentOutcomeRepository the repository for managing appointment outcome records
     * @param prescriptionRepository      the repository for managing prescription data
     * @param medicineRepository          the repository for managing medicine data
     * @param medicineRequestRepository   the repository for managing medicine replenishment requests
     * @param scanner                     the Scanner instance for user input
     */
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
        int choice = 0;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            if (input.matches("-?\\d+")) {  // Check if input is a valid integer (including negative numbers)
                choice = Integer.valueOf(input);
                handleChoice(choice);
                System.out.println("Press Enter to continue...");
                scanner.nextLine();  // Wait for Enter key
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
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
            ChangePage.changePage();
            System.out.println("==========================================");
            System.out.println("Welcome, " + administrator.getName() + "!");
            break;
        }
    }

    /**
     * Display the Administrator menu, showing the available options
     */
    public void displayMenu() {
        System.out.println("=========== Administrator MENU ===========");
        System.out.println("\t Staff management:");
        System.out.println("\t\t1.  Add Staff Member");
        System.out.println("\t\t2.  Update Staff Information");
        System.out.println("\t\t3.  Remove Staff Member");
        System.out.println("\t\t4.  View Staff List");
        System.out.println("\t Appointment management:");
        System.out.println("\t\t5.  View Scheduled Appointments");
        System.out.println("\t\t6.  View Appointment Outcome Record");
        System.out.println("\t Inventory management:");
        System.out.println("\t\t7. View Medicine Inventory Stock Levels");
        System.out.println("\t\t8. Add Medication to Inventory");
        System.out.println("\t\t9. Update Inventory Stock Levels");
        System.out.println("\t\t10. Remove Medication from Inventory");
        System.out.println("\t\t11. Set Low Stock Alert Level");
        System.out.println("\t\t12. View Low Stock Inventory Items");
        System.out.println("\t\t13. View All Replenishment Requests");
        System.out.println("\t\t14. Approve Replenishment Request");
        System.out.println("\t\t15. Logout");
        System.out.println("==========================================");
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
                viewAppointmentOutcomeRecord();
                break;
            case 7:
                viewMedicines();
                break;
            case 8:
                addMedicineToInventory();
                break;
            case 9:
                updateInventoryStockLevels();
                break;
            case 10:
                removeMedicationFromInventory();
                break;
            case 11:
                setLowStockAlertLevel();
                break;
            case 12:
                viewLowStockInventoryItems();
                break;
            case 13:
                showAllReplenishmentRequests();
                break;
            case 14:
                approveReplenishmentRequest();
                break;
            case 15:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }




    /**
     * Adds a new staff member to the system.
     * <p>
     * Prompts the administrator to enter the staff member's details such as ID, name, role,
     * password, gender, and age, and then adds the staff member to the system repository.
     * </p>
     */
    public void addStaff() {
        System.out.print("Enter the staff member's id: ");
        String id = scanner.nextLine();
        System.out.print("Enter the staff member's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the staff member's password: ");
        String password = scanner.nextLine();
        System.out.print("Enter the staff member's role: ");
        String role = scanner.nextLine();
        System.out.print("Enter the staff member's gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter the staff member's age: ");
        int age = Integer.valueOf(scanner.nextLine());

        Staff staff = new Staff(id, name, role, gender, age, password);
        staffRepository.addStaffRepo(staff);
    }

    /**
     * Updates the information of an existing staff member in the system.
     * <p>
     * Prompts the administrator to enter the staff member's ID, the field to update
     * (e.g., name, role, password, gender, or age), and the new value. The system
     * repository is then updated with the new information.
     * </p>
     */
    public void updateStaff() {
        System.out.print("Enter the staff member's id: ");
        String id = scanner.nextLine();
        System.out.print("Enter the field to be updated (name, role, password, gender, age): ");
        String field = scanner.nextLine();
        System.out.print("Enter the new value: ");
        String newValue = scanner.nextLine();
        staffRepository.updateStaffRepo(id, field, newValue);
    }

    /**
     * Removes a staff member from the system.
     * <p>
     * Prompts the administrator to enter the staff member's ID. If the ID matches
     * the administrator's own ID, the removal is not allowed. Otherwise, the staff
     * member is removed from the system repository.
     * </p>
     */
    public void removeStaff() {
        System.out.print("Enter the staff member's id: ");
        String id = scanner.nextLine();
        if (id.equals(administrator.getId())) {
            System.out.println("You cannot remove yourself.");
            return;
        }
        staffRepository.removeStaffRepo(id);
    }

    /**
     * Sorts a list of staff members based on the specified attribute.
     * <p>
     * Attributes for sorting include:
     * <ul>
     *     <li>Role</li>
     *     <li>Gender</li>
     *     <li>Age</li>
     * </ul>
     * </p>
     *
     * @param staffList the list of staff members to sort
     * @param displayBy the attribute to sort by (e.g., Role, Gender, Age)
     * @return the sorted list of staff members
     */
    private List<Staff> sortStaffList(List<Staff> staffList, String displayBy) {
        switch (displayBy) {
            case "Role":
                staffList.sort((s1, s2) -> s1.getRole().compareTo(s2.getRole()));
                break;
            case "Gender":
                staffList.sort((s1, s2) -> s1.getGender().compareTo(s2.getGender()));
                break;
            case "Age":
                staffList.sort((s1, s2) -> s1.getAge() - s2.getAge());
                break;
            default:
                break;
        }
        return staffList;
    }

    /**
     * Displays the list of staff members in the system.
     * <p>
     * The administrator is prompted to optionally sort the staff list by Role, Gender,
     * or Age. The staff members are then displayed in a formatted table.
     * </p>
     */
    public void viewStaffList() {
        List<Staff> staffList = staffRepository.getAllStaffs();
        if (staffList == null || staffList.isEmpty()) {
            System.out.println("No staff members.");
        } else {
            System.out.print("Display staff list by (Role/Gender/Age/empty to ignore): ");
            String displayBy = scanner.nextLine();
            staffList = sortStaffList(staffList, displayBy);

            System.out.println("Staff List:");
            System.out.println("+------------+----------------------+-----------------+------------+-----+");
            System.out.printf("| %-10s | %-20s | %-15s | %-10s | %-3s |\n",
                    "ID", "Name", "Role", "Gender", "Age");
            System.out.println("+------------+----------------------+-----------------+------------+-----+");

            for (Staff staff : staffList) {
                System.out.printf("| %-10s | %-20s | %-15s | %-10s | %-3d |\n",
                        staff.getId(),
                        staff.getName(),
                        staff.getRole(),
                        staff.getGender(),
                        staff.getAge());
            }

            System.out.println("+------------+----------------------+-----------------+------------+-----+");
        }
    }

    /**
     * Displays the list of scheduled appointments for all patients.
     * <p>
     * Includes details such as appointment ID, patient ID, doctor ID, date, timeslot,
     * and status, displayed in a formatted table.
     * </p>
     */
    public void viewScheduledAppointments() {
        List<Appointment> appointments = appointmentRepository.getAllAppointments();
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
        } else {
            System.out.println("Scheduled Appointments: ");
            System.out.println("+----+------------+------------+------------+----------+-----------+");
            System.out.printf("| %-2s | %-10s | %-10s | %-10s | %-8s | %-9s |\n",
                    "ID", "Patient ID", "Doctor ID", "Date", "Timeslot", "Status");
            System.out.println("+----+------------+------------+------------+----------+-----------+");

            for (Appointment appointment : appointments) {
                System.out.printf("| %-2d | %-10s | %-10s | %-10s | %-8d | %-18s |\n",
                        appointment.getId(),
                        appointment.getPatientId(),
                        appointment.getDoctorId(),
                        appointment.getDate(),
                        appointment.getTimeslot(),
                        ColorText.getStatusWithColor(appointment.getStatus()));
            }

            System.out.println("+----+------------+------------+------------+----------+-----------+");
        }
    }

    /**
     * Displays the outcome record of a specific appointment.
     * <p>
     * Prompts the administrator to enter the appointment ID, retrieves the
     * corresponding outcome record, and displays it if found.
     * </p>
     */
    public void viewAppointmentOutcomeRecord() {
        System.out.print("Enter the appointment id: ");
        int id = Integer.valueOf(scanner.nextLine());
        AppointmentOutcomeRecord record = appointmentOutcomeRepository.getAppointmentOutcomeRecordById(id);
        if (record == null) {
            System.out.println("Appointment outcome record not found.");
        } else {
            System.out.println(record.toString());
        }
    }


    /**
     * Add a new medicine to the inventory
     */
    public void addMedicineToInventory() {
        System.out.print("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        System.out.print("Enter current available stock: ");
        int stockLevel = Integer.valueOf(scanner.nextLine());
        System.out.print("Enter low threshold of the medicine: ");
        int lowThreshold = Integer.valueOf(scanner.nextLine());
        System.out.print("Enter high threshold of the medicine: ");
        int highThreshold = Integer.valueOf(scanner.nextLine());
        Medicine medicine = new Medicine(medicineName, stockLevel, lowThreshold, highThreshold);
        medicineRepository.addMedicine(medicine);
    }

    /**
     * Displays all medicines currently in the inventory.
     * <p>
     * The method retrieves and lists all medicines, including their name, stock level,
     * low stock threshold, and high stock threshold in a formatted table.
     * Medicines with stock levels below their low threshold are highlighted.
     * </p>
     */
    public void viewMedicines() {
        List<Medicine> medicines = medicineRepository.getAllMedicines();
        if (medicines == null || medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
        } else {
            System.out.println("Inventory Stock Levels:");
            System.out.println("+----------------+------------+------------+-------------+");
            System.out.printf("| %-14s | %-10s | %-10s | %-10s |\n", "Name", "Stock", "Low Thresh", "High Thresh");
            System.out.println("+----------------+------------+------------+-------------+");

            for (Medicine medicine : medicines) {
                boolean isLowStock = medicine.getStockLevel() < medicine.getLowThreshold();
                String rawStockLevel = String.valueOf(medicine.getStockLevel());
                String stockLevel = isLowStock
                        ? ColorText.getColoredText(rawStockLevel, ColorText.RED)
                        : ColorText.getColoredText(rawStockLevel, ColorText.GREEN);

                System.out.printf("| %-14s | %-19s | %-10d | %-11d |\n",
                        medicine.getName(),
                        stockLevel,
                        medicine.getLowThreshold(),
                        medicine.getHighThreshold());
            }

            System.out.println("+----------------+------------+------------+-------------+");
        }
    }

    /**
     * Updates the stock level of a specific medication in the inventory.
     * <p>
     * The administrator is prompted to provide the name of the medication
     * and its new quantity. If the medication does not exist, an error
     * message is displayed.
     * </p>
     */
    public void updateInventoryStockLevels() {
        System.out.print("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        if (!medicineRepository.medicineExists(medicineName)) {
            System.out.println("Medicine not found. Please try again.");
        } else {
            System.out.print("Enter new quantity for " + medicineName + ": ");
            int quantity = Integer.valueOf(scanner.nextLine());
            medicineRepository.updateStockLevel(medicineName, quantity);
        }
    }

    /**
     * Removes a medication from the inventory.
     * <p>
     * The administrator is prompted to provide the name of the medication.
     * If the medication exists, it is removed from the inventory. Otherwise,
     * an error message is displayed.
     * </p>
     */
    public void removeMedicationFromInventory() {
        System.out.print("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        if (!medicineRepository.medicineExists(medicineName)) {
            System.out.println("Medicine not found. Please try again.");
        } else {
            medicineRepository.removeMedicine(medicineName);
            System.out.println("Medicine " + medicineName + " removed from inventory.");
        }
    }

    /**
     * Sets a low stock alert level for a specific medication.
     * <p>
     * The administrator is prompted to provide the name of the medication
     * and a new low stock threshold. If the medication does not exist, an
     * error message is displayed.
     * </p>
     */
    public void setLowStockAlertLevel() {
        System.out.print("Enter the medication name: ");
        String medicineName = scanner.nextLine();
        if (!medicineRepository.medicineExists(medicineName)) {
            System.out.println("Medicine not found. Please try again.");
        } else {
            System.out.println("Enter the new low stock alert level: ");
            int lowThreshold = Integer.valueOf(scanner.nextLine());
            medicineRepository.updateLowThreshold(medicineName, lowThreshold);
        }
    }

    /**
     * Displays a list of medications with stock levels below their low threshold.
     * <p>
     * The method retrieves and lists all low stock medications, including their name,
     * stock level, low stock threshold, and high stock threshold in a formatted table.
     * </p>
     */
    public void viewLowStockInventoryItems() {
        List<Medicine> lowStockMedicines = medicineRepository.getLowStockMedicines();
        if (lowStockMedicines == null || lowStockMedicines.isEmpty()) {
            System.out.println("No low stock items.");
        } else {
            System.out.println("Low Stock Inventory Items:");
            System.out.println("+----------------+------------+------------+-------------+");
            System.out.printf("| %-14s | %-10s | %-10s | %-10s |\n", "Name", "Stock", "Low Thresh", "High Thresh");
            System.out.println("+----------------+------------+------------+-------------+");

            for (Medicine medicine : lowStockMedicines) {
                String rawStockLevel = String.valueOf(medicine.getStockLevel());
                String stockLevel = ColorText.getColoredText(rawStockLevel, ColorText.RED);

                System.out.printf("| %-14s | %-19s | %-10d | %-11d |\n",
                        medicine.getName(),
                        stockLevel,
                        medicine.getLowThreshold(),
                        medicine.getHighThreshold());
            }

            System.out.println("+----------------+------------+------------+-------------+");
        }
    }

    /**
     * Displays all replenishment requests for medications.
     * <p>
     * The method retrieves and lists all replenishment requests, including their ID,
     * the medicines requested, and their status in a formatted table.
     * </p>
     */
    public void showAllReplenishmentRequests() {
        List<MedicineRequest> requests = medicineRequestRepository.getAllMedicineRequests();
        if (requests == null || requests.isEmpty()) {
            System.out.println("No replenishment requests.");
        } else {
            System.out.println("Replenishment Requests:");
            System.out.println("+----+--------------------------+------------+");
            System.out.printf("| %-2s | %-24s | %-10s |\n", "ID", "Medicines", "Status");
            System.out.println("+----+--------------------------+------------+");

            for (MedicineRequest request : requests) {
                String medicines = String.join(", ", request.getMedicines());

                if (medicines.length() > 24) {
                    medicines = medicines.substring(0, 21) + "...";
                }

                System.out.printf("| %-2d | %-24s | %-10s |\n",
                        request.getId(),
                        medicines,
                        request.getStatus());
            }

            System.out.println("+----+--------------------------+------------+");
        }
    }

    /**
     * Approves a replenishment request for a specific medication.
     * <p>
     * The administrator is prompted to provide the request ID. If the request exists,
     * it is approved, and the inventory stock levels for the requested medicines are
     * updated to their high threshold. If the request does not exist, an error message
     * is displayed.
     * </p>
     */
    public void approveReplenishmentRequest() {
        System.out.print("Enter the request id: ");
        int id = Integer.valueOf(scanner.nextLine());
        MedicineRequest request = medicineRequestRepository.getMedicineRequestById(id);
        if (request == null) {
            System.out.println("Request not found.");
        } else {
            List<String> medicineNames = request.getMedicines();
            medicineRequestRepository.approveMedicineRequest(id);
            // Update in Medicine List
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
