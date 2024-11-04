package org.example.menu;

import org.example.entity.Administrator;

import java.util.Scanner;

public class AdministratorMenu implements Menu {
    public void displayMenu() {
        System.out.println("=====ADMINISTRATOR MENU=====");
        System.out.println("1. View and Manage Hospital Staff\n2. View Appointment Details\n3. View and Manage Medication Inventory\n4. Approve Replenishment Requests\n5. Logout");
    }

    public void handleChoice(int choice, User user) {
        Administrator admin = (Administrator) user;  // Cast User to Administrator
        switch (choice) {
            case 1:
                viewAndManageHospitalStaff(admin);
                break;
            case 2:
                viewAppointmentDetails(admin);
                break;
            case 3:
                viewAndManageMedicationInventory(admin);
                break;
            case 4:
                approveReplenishmentRequests(admin);
                break;
            case 5:
                logout(admin);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void start(User user) {
        Administrator admin = (Administrator) user;  // Cast User to Administrator
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            handleChoice(choice, admin);
        } while (choice != 5);  // Exit when logout is chosen
    }
}
