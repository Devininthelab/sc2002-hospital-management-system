package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;

import java.util.*;

public class PharmacistMenu implements Menu {
    private Scanner scanner;
    private StaffRepository staffRepository;
    private MedicineRepository medicineRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;
    private MedicineRequestRepository medicineRequestRepository;
    private PrescriptionRepository prescriptionRepository;
    private Pharmacist pharmacist;

    public PharmacistMenu(Scanner scanner, StaffRepository staffRepository, MedicineRepository medicineRepository, AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository, MedicineRequestRepository medicineRequestRepository, PrescriptionRepository prescriptionRepository) {
        this.scanner = scanner;
        this.staffRepository = staffRepository;
        this.medicineRepository = medicineRepository;
        this.appointmentOutcomeRecordRepository = appointmentOutcomeRecordRepository;
        this.medicineRequestRepository = medicineRequestRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Start the pharmacist menu.
     * <p>Pharmacist needs to log in. The menu will then display available options, and the user
     * will be prompted to choose an action. The menu will continue to run until the pharmacist logs out.</p>
     */
    public void start() {
        int choice = 0;
        login();
        while (choice != 6) {
            displayMenu();
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (choice >= 1 && choice <= 6) {
                    handleChoice(choice);
                    if (choice != 6) {
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();  // Wait for Enter key
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    /**
     * Log in the pharmacist to the system.
     * The pharmacist will be prompted to enter their user ID and password. If the credentials are
     * valid, the pharmacist is logged in, and the menu is displayed. If not, the system prompts the
     * pharmacist to re-enter the credentials until they are correct.
     */
    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            String id = scanner.nextLine();
            pharmacist = staffRepository.getPharmacistById(id);
            if (pharmacist == null) {
                System.out.println("Pharmacist not found. Try again");
                continue;
            }
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            if (pharmacist.getPassword().equals(password)) {
                System.out.println("You are logged in.");
                break;
            }

            System.out.println("Wrong password. Try again");
        }
        System.out.println("==========================================");
        System.out.printf("Welcome, %s!%n", pharmacist.getName());
    }

    /**
     * Display the main menu for the pharmacist.
     * The menu includes options for viewing appointment outcome records, dispensing
     * prescriptions, viewing medication inventory, submitting replenishment requests, updating
     * the password, and logging out.
     * If there are any medicines with low stock, a warning is displayed at the top of the menu.
     */
    public void displayMenu() {
        System.out.println("========== PHARMACIST MENU ============");

        if (medicineRepository.hasLowStockMedicines()) {
            System.out.println("WARNING: Low stock medicine. Please submit replenishment request.");
        }

        System.out.println("\t1. View Appointment Outcome Record");
        System.out.println("\t2. Dispense Prescription");
        System.out.println("\t3. View Medication Inventory");
        System.out.println("\t4. Submit Replenishment Request");
        System.out.println("\t5. Update Password");
        System.out.println("\t6. Logout");
        System.out.println("==========================================");
    }

    /**
     * Handle the pharmacist's menu choice.
     * Each choice corresponds to a specific action, such as viewing appointment outcome records,
     * dispensing prescriptions, or updating the password. The method redirects to the appropriate
     * handler function based on the user's input.
     * @param choice The selected menu option
     */
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                viewAppointmentOutcomeRecord();
                break;
            case 2:
                dispensePrescription();
                break;
            case 3:
                viewMedicationInventory();
                break;
            case 4:
                submitReplenishmentRequest();
                break;
            case 5:
                updatePassword();
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    /**
     * Display all appointment outcome records that have pending prescriptions.
     * This allows the pharmacist to see which appointments require prescription dispensing.
     */
    public void viewAppointmentOutcomeRecord() {
        List<AppointmentOutcomeRecord> records = appointmentOutcomeRecordRepository.getAllPendingRecords();
        if (records.isEmpty()) {
            System.out.println("No pending appointments found.");
        }
        for (AppointmentOutcomeRecord record : records) {
            System.out.println(record);
        }
    }

    /**
     * Dispense pending prescriptions for an appointment.
     * The pharmacist is prompted to enter an appointment ID, view the pending prescriptions,
     * and mark them as dispensed. The pharmacist can dispense multiple prescriptions in one session.
     */
    public void dispensePrescription() {
        int appointmentId = -1;

        // Loop to ensure a valid appointment ID is entered
        while (true) {
            try {
                System.out.print("Enter appointment id: ");
                appointmentId = Integer.valueOf(scanner.nextLine());
                break; // Exit loop if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric appointment ID.");
            }
        }

        AppointmentOutcomeRecord record = appointmentOutcomeRecordRepository.getRecordById(appointmentId);
        if (record == null) {
            System.out.println("Appointment Outcome Record not found or Appointment does not exist.");
            return;
        }
        List<Prescription> pendingPrescriptions = prescriptionRepository.getPendingPrescriptions(appointmentId);
        if (pendingPrescriptions.isEmpty()) {
            System.out.println("No pending prescriptions for this appointment.");
            return;
        }

        System.out.println("Pending prescriptions for appointment ID " + appointmentId + ":");
        for (Prescription prescription : pendingPrescriptions) {
            System.out.println("- " + prescription.getName() + " (Quantity: " + prescription.getQuantity() + ")");
        }

        while (true) {
            System.out.print("Enter name of prescription to dispense (empty to finish): ");
            String prescriptionName = scanner.nextLine();

            // Exit if input is empty
            if (prescriptionName.isEmpty()) {
                System.out.println("Finish dispensing.");
                break;
            }

            Prescription prescriptionToDispense = null;
            for (Prescription prescription : pendingPrescriptions) {
                if (prescription.getName().equalsIgnoreCase(prescriptionName)) {
                    prescriptionToDispense = prescription;
                    break;
                }
            }

            if (prescriptionToDispense == null) {
                System.out.println("Prescription not found in pending list. Please try again.");
                continue;
            }

            // Check if the prescription has already been dispensed
            if (prescriptionToDispense.getStatus().equalsIgnoreCase("DISPENSED")) {
                System.out.println("This prescription has already been dispensed. Please select another.");
                continue;
            }

            // Check if the medicine exists in the repository
            if (!medicineRepository.medicineExists(prescriptionName)) {
                System.out.println("Invalid medicine name. Try again.");
                continue;
            }

            // Check stock level
            int quantity = prescriptionToDispense.getQuantity();
            if (medicineRepository.getMedicine(prescriptionName).getStockLevel() < quantity) {
                System.out.println("Medicine stock level not adequate. Please submit replenishment request.");
                continue;
            }

            // Dispense the medicine
            prescriptionRepository.updatePrescriptionStatus(appointmentId, prescriptionName, "DISPENSED");
            medicineRepository.decreaseStockLevel(prescriptionName, quantity);

            System.out.println("Prescription " + prescriptionName + " dispensed successfully.");
        }
    }

    /**
     * View the inventory of all medicines.
     * The pharmacist can choose to view only low-stock medicines or all medicines.
     * The inventory is displayed in a formatted table with medicines that are low on stock highlighted.
     */
    public void viewMedicationInventory() {
        System.out.println("Medicine inventory");
        String choice;

        // Loop until valid input (Y/N) is provided
        while (true) {
            System.out.print("Do you want to see only low stock medicine? (Y/N): ");
            choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
        }

        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Low stock medicine: ");
            List<Medicine> lowStockMedicines = medicineRepository.getLowStockMedicines();
            printMedicineTable(lowStockMedicines);
        } else {
            System.out.println("All medicines: ");
            List<Medicine> medicines = medicineRepository.getAllMedicines();
            printMedicineTable(medicines);
        }
    }

    /**
     * Print a formatted table of medicines.
     * @param medicines List of medicines to display
     */
    private void printMedicineTable(List<Medicine> medicines) {
        // Print the table header with borders
        System.out.println("+----------------------+----------+---------------+---------------+");
        System.out.printf("| %-20s | %-8s | %-13s | %-13s |%n",
                "Name", "Stock", "Low Thresh", "High Thresh");
        System.out.println("+----------------------+----------+---------------+---------------+");

        // Print each medicine with dynamic stock color
        for (Medicine medicine : medicines) {
            String stockColor = medicine.isLowStock() ? "\u001B[31m" : "\u001B[32m"; // Red for low stock, green otherwise
            String resetColor = "\u001B[0m"; // Reset to default color

            System.out.printf("| %-20s | " + stockColor + "%-8d" + resetColor + " | %-13d | %-13d |%n",
                    medicine.getName(),
                    medicine.getStockLevel(),
                    medicine.getLowThreshold(),
                    medicine.getHighThreshold());
        }

        // Print the table footer
        System.out.println("+----------------------+----------+---------------+---------------+");
    }

    /**
     * Submit a replenishment request for low stock medicines.
     * The pharmacist can review the low stock medicines and select which ones to request
     * for replenishment. The request is then submitted to the {@link MedicineRequestRepository}.
     */
    public void submitReplenishmentRequest() {
        System.out.println("Submit replenishment request");

        // Fetch low stock medicines
        List<Medicine> lowStockMedicines = medicineRepository.getLowStockMedicines();

        // Check if there are any low stock medicines
        if (lowStockMedicines.isEmpty()) {
            System.out.println("No medicines are in low stock.");
            return;
        }

        // Display low stock medicines
        System.out.println("Low stock medicines:");
        lowStockMedicines.forEach(medicine ->
                System.out.println("- " + medicine.getName())
        );

        // Collect medicines for replenishment request
        Set<String> medicines = new LinkedHashSet<>();
        while (true) {
            System.out.print("Enter medicine name to request replenishment (empty to finish): ");
            String medicineName = scanner.nextLine().trim();

            // Exit loop if input is empty
            if (medicineName.isEmpty()) {
                break;
            }

            // Check if the entered medicine is in the low stock list
            boolean isLowStock = lowStockMedicines.stream()
                    .anyMatch(medicine -> medicine.getName().equalsIgnoreCase(medicineName));

            if (!isLowStock) {
                System.out.println("This medicine is not low on stock or does not exist. Try again.");
                continue;
            }

            // Check if the medicine is already in the set
            if (medicines.contains(medicineName.toLowerCase())) {
                System.out.println("This medicine has already been added to the request. Try another.");
                continue;
            }

            // Add to the request set if valid
            medicines.add(medicineName.toLowerCase());
            System.out.println("Medicine has been added to the request successfully."); // Normalize to avoid case sensitivity issues
        }

        // Review and confirm the replenishment request
        if (medicines.isEmpty()) {
            System.out.println("No medicines were selected for replenishment.");
            return;
        }

        System.out.println("Review replenishment request:");
        medicines.forEach(System.out::println);

        System.out.print("Submit request? (Y/N): ");
        String choice = scanner.nextLine().trim();

        while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
            System.out.println("Invalid choice. Please enter 'Y' for yes or 'N' for no.");
            System.out.print("Submit request? (Y/N): ");
            choice = scanner.nextLine().trim();
        }

        if (choice.equalsIgnoreCase("N")) {
            System.out.println("Request cancelled.");
            return;
        }

        medicineRequestRepository.addMedicineRequest(new ArrayList<>(medicines));
        System.out.println("Replenishment request submitted.");
    }

    /**
     * Update the password for the logged-in pharmacist.
     * The pharmacist is prompted to enter a new password, which is then updated in the
     * {@link StaffRepository}.
     */
    public void updatePassword() {
        String newPassword = "";
        while (newPassword.length() < 6 || newPassword.equals(pharmacist.getPassword())) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine();
            if (newPassword.length() < 6) {
                System.out.println("Password must be at least 6 characters.");
            }
            if (newPassword.equals(pharmacist.getPassword())) {
                System.out.println("Same password. Please change to the new password.");
            }
        }
        pharmacist.setPassword(newPassword);
        staffRepository.updatePassword(pharmacist.getId(), newPassword);
        System.out.println("Password updated successfully.");
    }
}