package org.example.menu;


import org.example.entity.Medicine;
import org.example.entity.Pharmacist;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.repository.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Pharmacist menu
 * Pharmacist can view appointment outcome record, update prescription status, view medication inventory, submit replenishment request
 * Menu keeps a pharmacist session id to keep track of user's state
 * The following dependencies are injected:
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
    private MedicationRepository medicationRepository;
    private Pharmacist pharmacist;

    /**
     * Constructor to inject dependencies
     * @param scanner
     * @param staffRepository
     * @param medicineRepository
     * @param appointmentOutcomeRecordRepository
     * @param medicineRequestRepository
     * @param medicationRepository
     */
    public PharmacistMenu(Scanner scanner, StaffRepository staffRepository, MedicineRepository medicineRepository, AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository, MedicineRequestRepository medicineRequestRepository, MedicationRepository medicationRepository) {
        this.scanner = scanner;
        this.staffRepository = staffRepository;
        this.medicineRepository = medicineRepository;
        this.appointmentOutcomeRecordRepository = appointmentOutcomeRecordRepository;
        this.medicineRequestRepository = medicineRequestRepository;
        this.medicationRepository = medicationRepository;
    }

    /**
     * Start pharmacist menu
     * Pharmacist login
     * Display choice for user to choose and redirect to respective handler function
     */
    public void start() {
        int choice;
        login();
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            handleChoice(choice);
        } while (choice != 5);  // Exit when logout is chosen
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
        System.out.println("1. View Appointment Outcome Record\n" +
                "2. Update Prescription Status\n" +
                "3. View Medication Inventory\n" +
                "4. Submit Replenishment Request\n" +
                "5. Logout");
    }

    /**
     * redirect to view functions with corresponding responsibility
     * @param choice
     */
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                viewAppointmentOutcomeRecord();
                break;
            case 2:
                updatePrescriptionStatus();
                break;
            case 3:
                viewMedicationInventory();
                break;
            case 4:
                submitReplenishmentRequest();
                break;
            case 5:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
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
     * Update prescription status in appointment outcome record
     * Prompt pharmacist for record id, and print out the detail of that id
     * Then repeated prompt for prescription with new status,
     * save through appointment outcome record, until user enter empty line
     */
    public void updatePrescriptionStatus() {
        System.out.print("Enter appointment id: ");
        int appointmentId = scanner.nextInt();
        System.out.println(appointmentOutcomeRecordRepository.getRecordById(appointmentId));
        while (true) {
            System.out.print("Enter name of prescription with changed status (empty to finish): ");
            String presciptionName = scanner.nextLine();
            if (presciptionName.isEmpty()) {
                break;
            }

            //TODO: Input validation

            System.out.print("Enter new prescription status: ");
            String status = scanner.nextLine();
            medicationRepository.updateMedicationStatus(appointmentId, presciptionName, status);
            if (status.equals("DISPENSED")) {
                int quantity = medicationRepository.getMedicationsByNameAndId(appointmentId, presciptionName).getQuantity();
                medicineRepository.decreaseStockLevel(presciptionName, quantity);
            }

        }
        System.out.println("Prescription status updated.");
    }


    public void viewMedicationInventory() {
        System.out.println("Medication inventory");
        List<Medicine> medicines = medicineRepository.getMedicines();
        medicines.forEach(System.out::println);
    }

    /**
     * Prompt pharmacist for list of medicine, submit request to request list for admin to see
     */
    public void submitReplenishmentRequest() {
        System.out.println("Replenishment request");
        List<String> medicines = new ArrayList<>();
        while (true) {
            System.out.print("Enter medicine name (empty to finish): ");
            String medicineName = scanner.nextLine();
            if (medicineName.trim().isEmpty()) {
                break;
            }

            if (!medicineRepository.medicineExists(medicineName)) {
                System.out.println("Medicine not found. Try again");
                continue;
            }
            
            medicines.add(medicineName);
        }

        medicineRequestRepository.addMedicineRequest(medicines);
    }
}
