package org.example.menu;

import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private MenuFactory menuFactory = new MenuFactory();

    /**
     * Start the user menu, should run first when the program starts
     */
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
                roleRedirect();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    /**
     * Redirect to the corresponding menu based on the role chosen
     */
    public void roleRedirect() {
        System.out.println("Log in using your id and password");
        System.out.println("Enter your role:\n" +
                "1. Patient\n" +
                "2. Doctor\n" +
                "3. Pharmacist\n" +
                "4. Administrator");
        int role = scanner.nextInt();
        String[] roles = {"Patient", "Doctor", "Pharmacist", "Administrator"};
        if (role < 1 || role > 4) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        Menu menu = menuFactory.createMenu(roles[role - 1]); // Redirect to the corresponding menu
        menu.start();
        //switch (role) {
        //    case 1:
        //        PatientMenu patientMenu = new PatientMenu();
        //        patientMenu.start(); // Redirect to patient menu
        //        break;
        //    case 2:
        //        DoctorMenu doctorMenu = new DoctorMenu();
        //        doctorMenu.start(); // Redirect to doctor menu
        //        break;
        //    case 3:
        //        PharmacistMenu pharmacistMenu = new PharmacistMenu();
        //        pharmacistMenu.start(); // Redirect to pharmacist menu
        //        break;
        //    case 4:
        //        AdministratorMenu administratorMenu = new AdministratorMenu();
        //        administratorMenu.start();  // Redirect to administrator menu
        //        break;
        //    default:
        //        System.out.println("Invalid choice. Please try again.");
        //}
    }
}
