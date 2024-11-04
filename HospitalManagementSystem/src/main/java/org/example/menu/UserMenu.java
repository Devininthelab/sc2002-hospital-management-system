package org.example.menu;

import java.util.Scanner;

public class UserMenu {
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Welcome" +
                "\n1. Log in" +
                "\n2. Quit");
        while (true) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 2) {
                break;
            }

            if (choice == 1) {
                login();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public void login() {
        System.out.println("Log in using your id and password");
        System.out.println("Enter your role:\n" +
                "1. Patient\n" +
                "2. Staff");
        int role = scanner.nextInt();
        switch (role) {
            case 1:
                PatientMenu patientMenu = new PatientMenu();
                patientMenu.start();
                break;
            case 2:
                DoctorMenu doctorMenu = new DoctorMenu();
                doctorMenu.start();
                break;
            case 3:
                PharmacistMenu pharmacistMenu = new PharmacistMenu();
                pharmacistMenu.start();
                break;
            case 4:
                AdministratorMenu administratorMenu = new AdministratorMenu();
                administratorMenu.start();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
