package org.example.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
        loadUsersFromCSV("src/main/resources/userdb.csv");
    }

    private void loadUsersFromCSV(String csvPath) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String username = values[0].trim();
                String password = values[1].trim();
                String role = values[2].trim();
                String contact = values[3].trim();
                int id = Integer.parseInt(values[4].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number");
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
