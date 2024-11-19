package org.example.menu;

import org.example.utils.ChangePage;

import java.util.Scanner;


/**
 * The MainMenu class provides the entry point for the Hospital Management System.
 * <p>
 * It allows users to log in and navigate to their respective roles (Patient, Doctor, Pharmacist, or Administrator).
 * </p>
 */
public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private MenuFactory menuFactory = new MenuFactory();

    /**
     * Starts the main menu of the Hospital Management System.
     * <p>
     * Displays a welcome message and provides options for logging in or quitting the system.
     * If the user chooses to log in, they are redirected to their role-specific menu.
     * </p>
     */
    public void start() {
        System.out.println("          _____                    _____                    _____          \n" +
                "         /\\    \\                  /\\    \\                  /\\    \\         \n" +
                "        /::\\____\\                /::\\____\\                /::\\    \\        \n" +
                "       /:::/    /               /::::|   |               /::::\\    \\       \n" +
                "      /:::/    /               /:::::|   |              /::::::\\    \\      \n" +
                "     /:::/    /               /::::::|   |             /:::/\\:::\\    \\     \n" +
                "    /:::/____/               /:::/|::|   |            /:::/__\\:::\\    \\    \n" +
                "   /::::\\    \\              /:::/ |::|   |            \\:::\\   \\:::\\    \\   \n" +
                "  /::::::\\    \\   _____    /:::/  |::|___|______    ___\\:::\\   \\:::\\    \\  \n" +
                " /:::/\\:::\\    \\ /\\    \\  /:::/   |::::::::\\    \\  /\\   \\:::\\   \\:::\\    \\ \n" +
                "/:::/  \\:::\\    /::\\____\\/:::/    |:::::::::\\____\\/::\\   \\:::\\   \\:::\\____\\\n" +
                "\\::/    \\:::\\  /:::/    /\\::/    / ~~~~~/:::/    /\\:::\\   \\:::\\   \\::/    /\n" +
                " \\/____/ \\:::\\/:::/    /  \\/____/      /:::/    /  \\:::\\   \\:::\\   \\/____/ \n" +
                "          \\::::::/    /               /:::/    /    \\:::\\   \\:::\\    \\     \n" +
                "           \\::::/    /               /:::/    /      \\:::\\   \\:::\\____\\    \n" +
                "           /:::/    /               /:::/    /        \\:::\\  /:::/    /    \n" +
                "          /:::/    /               /:::/    /          \\:::\\/:::/    /     \n" +
                "         /:::/    /               /:::/    /            \\::::::/    /      \n" +
                "        /:::/    /               /:::/    /              \\::::/    /       \n" +
                "        \\::/    /                \\::/    /                \\::/    /        \n" +
                "         \\/____/                  \\/____/                  \\/____/         \n" +
                "                                                                           ");

        while (true) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.println("==========================================");
            System.out.println("\n1. Log in" +
                    "\n2. Quit");
            System.out.print("Enter your choice (1-2): ");

            int choice = getValidatedInt();

            if (choice == 2) {
                break;
            }

            if (choice == 1) {
                ChangePage.changePage();
                roleRedirect();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Redirects the user to their role-specific menu.
     * <p>
     * The user is prompted to select their role (Patient, Doctor, Pharmacist, or Administrator).
     * The corresponding menu is then launched based on their choice.
     * </p>
     */
    public void roleRedirect() {
        System.out.println("===============================================");
        System.out.println(
                "\t\t1. Patient\n" +
                "\t\t2. Doctor\n" +
                "\t\t3. Pharmacist\n" +
                "\t\t4. Administrator");
        System.out.println("===============================================");
        System.out.print("Please enter your role (1-4): ");

        int role = getValidatedInt();

        String[] roles = {"PATIENT", "DOCTOR", "PHARMACIST", "ADMINISTRATOR"};
        if (role < 1 || role > 4) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        Menu menu = menuFactory.createMenu(roles[role - 1]); // Redirect to the corresponding menu
        menu.start();
    }

    /**
     * Validates and retrieves an integer input from the user.
     * <p>
     * If the input is invalid (e.g., not a number), the user is prompted to enter a valid number.
     * </p>
     *
     * @return a valid integer input from the user
     */
    private int getValidatedInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Warning: Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return number;
    }
}
