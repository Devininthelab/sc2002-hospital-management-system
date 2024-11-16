package org.example.menu;

import org.example.entity.*;
import org.example.entity.Medicine;
import org.example.entity.Pharmacist;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.repository.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

/**
 * Pharmacist menu
 * <p>Pharmacist can view appointment outcome record, update prescription status, view medication inventory, submit replenishment request</p>
 * <p>Show persistence warning when stock level is low</p>
 * <p>Menu keeps a pharmacist session id to keep track of user's state
 * The following dependencies are injected:</p>
 * - StaffRepository - Pharmacist login
 * - Medicine repository - View medication inventory, submit replenishment request
 * - AppointmentOutcomeRecordRepository - View appointment outcome record, update prescription status
 * - MedicineRequestRepository - Submit replenishment request
 * - MedicationRepository - Update prescription status
 */
public class PharmacistMenu implements Menu {
    private Scanner scanner;
    private StaffRepository staffRepository;
    private MedicineRepository medicineRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;
    private MedicineRequestRepository medicineRequestRepository;
    private PrescriptionRepository prescriptionRepository;
    private Pharmacist pharmacist;

    /**
     * Constructor to inject dependencies
     * @param scanner
     * @param staffRepository
     * @param medicineRepository
     * @param appointmentOutcomeRecordRepository
     * @param medicineRequestRepository
     * @param prescriptionRepository
     */
    public PharmacistMenu(Scanner scanner, StaffRepository staffRepository, MedicineRepository medicineRepository, AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository, MedicineRequestRepository medicineRequestRepository, PrescriptionRepository prescriptionRepository) {
        this.scanner = scanner;
        this.staffRepository = staffRepository;
        this.medicineRepository = medicineRepository;
        this.appointmentOutcomeRecordRepository = appointmentOutcomeRecordRepository;
        this.medicineRequestRepository = medicineRequestRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Start pharmacist menu
     * Pharmacist login
     * Display choice for user to choose and redirect to respective handler function
     */
    public void start() {
        int choice = 0;
        login();
        while (choice != 6){
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = Integer.valueOf(scanner.nextLine());
            handleChoice(choice);
        }  // Exit when logout is chosen
    }

    /**
     * Prompt pharmacist for id and password
     * Call staffRepository to get pharmacist object
     * If id is not a valid pharmacist id, or password does not match, pharmacist
     */
    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            String id = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            pharmacist = staffRepository.getPharmacistById(id);
            if (pharmacist == null) {
                System.out.println("Pharmacist not found. Try again");
                continue;
            }

            if (pharmacist.getPassword().equals(password)) {
                System.out.println("You are logged in.");
                break;
            }

            System.out.println("Wrong password. Try again");
        }
    }

    /**
     * Display pharmacist menu
     */
    public void displayMenu() {
        System.out.println("=====PHARMACIST MENU=====");
        if (medicineRepository.hasLowStockMedicines()) {
            System.out.println("WARNING: Low stock medicine. Please submit replenishment request.");
        }
        System.out.println("1. View Appointment Outcome Record\n" +
                "2. Dispense Prescription\n" +
                "3. View Medication Inventory\n" +
                "4. Submit Replenishment Request\n" +
                "5. Update password\n" +
                "6. Logout");
    }

    /**
     * redirect to view functions with corresponding responsibility
     * @param choice
     */
    public void handleChoice(int choice) {
        scanner.nextLine();
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
     * Display all appointment outcome record with a pending prescription
     */
    public void viewAppointmentOutcomeRecord() {
        List<AppointmentOutcomeRecord> records = appointmentOutcomeRecordRepository.getAllPendingRecords();
        for (AppointmentOutcomeRecord record : records) {
            System.out.println(record);
        }
    }

    /**
     * Dispense prescription status in appointment outcome record
     * Prompt pharmacist for record id, and print out the detail of that id
     * Then repeated prompt for prescription to dispense
     * save through appointment outcome record, until user enter empty line
     */
    /**TODO: May print all appointment id existing first, then enter appointment id*/
    public void dispensePrescription() {
        System.out.print("Enter appointment id: ");
        int appointmentId = Integer.valueOf(scanner.nextLine());
        AppointmentOutcomeRecord record = appointmentOutcomeRecordRepository.getRecordById(appointmentId);
        List<Prescription> pendingPrescriptions = prescriptionRepository.getPendingPrescriptions(appointmentId);
        if (pendingPrescriptions.isEmpty()) {
            System.out.println("No pending prescriptions for this appointment.");
            return;
        }

        Set<String> pendingPrescriptionNames = new HashSet<>();
        for (Prescription prescription : pendingPrescriptions) {
            pendingPrescriptionNames.add(prescription.getName());
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
                break;
            }

            // Check if the prescription is in the pending list
            if (!pendingPrescriptionNames.contains(prescriptionName)) {
                System.out.println("Prescription not found in pending list. Please try again.");
                continue;
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
     * Display all medicine in inventory
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
            lowStockMedicines.forEach(System.out::println);
        } else {
            System.out.println("All medicines: ");
            List<Medicine> medicines = medicineRepository.getAllMedicines();
            medicines.forEach(System.out::println);
        }
    }

    /**
     * Prompt pharmacist for list of medicine, submit request to request list for admin to see
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
            medicines.add(medicineName.toLowerCase()); // Normalize to avoid case sensitivity issues
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

    public void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();
        pharmacist.setPassword(newPassword);
        staffRepository.updatePassword(pharmacist.getId(), newPassword);
        System.out.println("Password updated.");
    }
}
