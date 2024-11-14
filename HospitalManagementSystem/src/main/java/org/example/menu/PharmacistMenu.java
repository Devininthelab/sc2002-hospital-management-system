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
    public void dispensePrescription() {
        System.out.print("Enter appointment id: ");
        int appointmentId = scanner.nextInt();
        AppointmentOutcomeRecord record = appointmentOutcomeRecordRepository.getRecordById(appointmentId);
        System.out.println(record);
        while (true) {
            System.out.print("Enter name of prescription to dispense (empty to finish): ");
            String presciptionName = scanner.nextLine();
            if (presciptionName.isEmpty()) {
                break;
            }
            // Check if medicine name is valid
            if (!medicineRepository.medicineExists(presciptionName)
                || !prescriptionRepository.isValidPrescription(appointmentId, presciptionName)) {
                System.out.println("Invalid medicine name. Try again");
                continue;
            }
            // Check if medicine has enough stock level
            // get quantity
            int quantity = prescriptionRepository.getPrescriptionsByNameAndId(appointmentId, presciptionName).getQuantity();
            if (medicineRepository.getMedicine(presciptionName).getStockLevel() < quantity) {
                System.out.println("Medicine stock level not adequate. Please submit replishment request");
                continue;
            }
            prescriptionRepository.updatePrescriptionStatus(appointmentId, presciptionName, "DISPENSED");
            medicineRepository.decreaseStockLevel(presciptionName, quantity);
            System.out.println("Prescription status updated.");
        }

    }

    /**
     * Display all medicine in inventory
     */
    public void viewMedicationInventory() {
        System.out.println("Medicince inventory");
        System.out.println("Do you want to see only low stock medicine? (Y/N)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            List<Medicine> lowStockMedicines = medicineRepository.getLowStockMedicines();
            lowStockMedicines.forEach(System.out::println);
        } else {
            List<Medicine> medicines = medicineRepository.getAllMedicines();
            medicines.forEach(System.out::println);
        }
    }

    /**
     * Prompt pharmacist for list of medicine, submit request to request list for admin to see
     */
    public void submitReplenishmentRequest() {
        System.out.println("Submit replenishment request");
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
        System.out.println("Review replenishment request");
        medicines.forEach(System.out::println);
        System.out.println("Submit request? (Y/N)");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("Y")) {
            System.out.println("Request cancelled.");
            return;
        }

        medicineRequestRepository.addMedicineRequest(medicines);
        System.out.println("Replenishment request submitted.");
    }

    public void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        pharmacist.setPassword(newPassword);
        staffRepository.updatePassword(pharmacist.getId(), newPassword);
        System.out.println("Password updated.");
    }
}
