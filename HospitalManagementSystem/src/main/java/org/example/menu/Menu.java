package org.example.menu;

import org.example.entity.User;

public interface Menu {
    void displayMenu();
    void handleChoice(int choice, User user);
    void start(User user);  // Updated to accept User for polymorphism
}