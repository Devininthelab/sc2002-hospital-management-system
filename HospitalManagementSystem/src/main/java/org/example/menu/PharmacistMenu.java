package org.example.menu;

import org.example.entity.Pharmacist;
import org.example.entity.User;
import java.util.Scanner;

public class PharmacistMenu implements Menu {
    public void displayMenu() {
        System.out.println("=====PHARMACIST MENU=====");
        System.out.println("1. View Appointment Outcome Record\n2. Update Prescription Status\n3. View Medication Inventory\n4. Submit Replenishment Request\n5. Logout");
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

    public void start(User user) {
        Pharmacist pharmacist = (Pharmacist) user;  // Cast User to Pharmacist
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            handleChoice(choice, pharmacist);
        } while (choice != 5);  // Exit when logout is chosen
    }
}
