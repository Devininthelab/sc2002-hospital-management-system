package org.example.menu;

import org.example.entity.Medicine;
import org.example.entity.Pharmacist;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.repository.AppointmentOutcomeRecordRepository;
import org.example.repository.MedicineRepository;
import org.example.repository.StaffRepository;

import java.util.List;
import java.util.Scanner;

public class PharmacistMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final StaffRepository staffRepository = new StaffRepository();
    private final MedicineRepository medicineRepository = new MedicineRepository();
    private final AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository = new AppointmentOutcomeRecordRepository();
    private Pharmacist pharmacist;

    public void displayMenu() {
        System.out.println("=====PHARMACIST MENU=====");
        System.out.println("1. View Appointment Outcome Record\n" +
                "2. Update Prescription Status\n" +
                "3. View Medication Inventory\n" +
                "4. Submit Replenishment Request\n" +
                "5. Logout");
    }

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
     * redirect to view functions with corresponding responsibility
     * @param choice
     */
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                viewAppointmentOutcomeRecord(pharmacist);
                break;
            case 2:
                updatePrescriptionStatus(pharmacist);
                break;
            case 3:
                viewMedicationInventory(pharmacist);
                break;
            case 4:
                submitReplenishmentRequest(pharmacist);
                break;
            case 5:
                logout(pharmacist);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /**
     * Display all appointment outcome record with a pending prescription
     * @param pharmacist
     */
    public void viewAppointmentOutcomeRecord(Pharmacist pharmacist) {
        List<AppointmentOutcomeRecord> records = appointmentOutcomeRecordRepository.getAllPendingRecords();
        for (AppointmentOutcomeRecord record : records) {
            System.out.println(record);
        }
    }

    public void updatePrescriptionStatus(Pharmacist pharmacist) {
        System.out.print("Enter appointment id: ");
        int appointmentId = scanner.nextInt();
        System.out.println(appointmentOutcomeRecordRepository.getRecordById(appointmentId));
        System.out.print("Enter new prescription status: ");
        String status = scanner.nextLine();
        appointmentOutcomeRecordRepository.updatePrescriptionStatus(appointmentId, status);
    }

    public void viewMedicationInventory(Pharmacist pharmacist) {
        List<Medicine> medicines = medicineRepository.getMedicines();
        medicines.forEach(System.out::println);
    }

    public void submitReplenishmentRequest(Pharmacist pharmacist) {
        
    }
}
