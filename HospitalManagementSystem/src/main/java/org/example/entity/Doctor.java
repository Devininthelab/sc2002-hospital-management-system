package org.example.entity;

import java.util.ArrayList;
import java.util.List;

/**3 STATUS FOR SCHEDULE: AVAILABLE, BUSY, BOOKED*/
public class Doctor extends Staff {
    private String[][] schedule;

    public Doctor(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);
        schedule = new String[8][6];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                schedule[i][j] = "AVAILABLE";
            }
        }
    }

    public String[][] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[][] schedule) {
        this.schedule = schedule;
    }

    public void printSchedule() {
        System.out.printf("| %-8s | %-11s | %-11s | %-11s | %-11s | %-11s | %-11s |%n", "Timeslot", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");
        for (int i = 0; i < 8; i++) {
            System.out.printf("| %-8s ", (9 + i) + ":00");
            for (int j = 0; j < 6; j++) {
                System.out.printf("| %-11s ", schedule[i][j]);
            }
            System.out.println("|");
        }
    }
}
