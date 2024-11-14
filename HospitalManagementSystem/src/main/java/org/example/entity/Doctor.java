package org.example.entity;

import java.util.ArrayList;
import java.util.List;

/**3 STATUS FOR SCHEDULE: AVAILABLE, BUSY, BOOKED*/
public class Doctor extends Staff {
    private String[][] schedule;
    private List<Appointment> appointments;

    public Doctor(String id, String name, String role, String password, String gender, int age) {
        super(id, name, role, gender, age, password);
        schedule = new String[8][6];
        appointments = new ArrayList<>();

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

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void printSchedule() {
        System.out.printf("| %-9s | %-9s | %-9s | %-9s | %-9s | %-9s | %-9s |%n", "", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");
        for (int i = 0; i < 8; i++) {
            System.out.printf("| %-9s ", (9 + i) + ":00");
            for (int j = 0; j < 6; j++) {
                System.out.printf("| %-9s ", schedule[i][j]);
            }
            System.out.println("|");
        }
    }
}
