package org.example.menu;

import org.example.control.AccountService;
import org.example.entity.User;

import java.util.Scanner;

public interface Menu {
    void displayMenu();
    void handleChoice(int choice);
    void start();  // Updated to accept User for polymorphism
}