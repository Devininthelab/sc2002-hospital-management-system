package org.example.menu;

public interface Menu {
    void displayMenu();
    void handleChoice(int choice);
    void start();  // Updated to accept User for polymorphism
}