package org.example.menu;

/**
 * Menu is an interface that defines the methods required for a menu in the system.
 * <p>
 * The interface includes methods to display the menu, handle user choices, and start the menu.
 * </p>
 */
public interface Menu {
    /**
     * Displays the menu options to the user.
     */
    void displayMenu();

    /**
     * Handles the user's choice based on the input.
     *
     * @param choice the user's choice
     */
    void handleChoice(int choice);

    /**
     * Gets an integer input from the user.
     */
    void login();

    /**
     * Starts the menu.
     */
    void start();  // Updated to accept User for polymorphism
}