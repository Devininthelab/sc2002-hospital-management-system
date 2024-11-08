package org.example.menu;

import org.example.entity.Pharmacist;
import org.example.repository.AppointmentOutcomeRecordRepository;
import org.example.repository.MedicineRepository;
import org.example.repository.StaffRepository;

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

    public void handleChoice(int choice, User user) {
        Pharmacist pharmacist = (Pharmacist) user;  // Cast User to Pharmacist
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


}
