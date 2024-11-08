package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff {
    private String[][] schedule;
    private List<Appointment> appointments;

    public Doctor(String id, String name, String role, String password, Patient.Gender gender, int age) {
        super(id, name, role, gender, age, password);
        schedule = new String[20][7];
        appointments = new ArrayList<>();
    }

    public String[][] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[][] schedule) {
        this.schedule = schedule;
    }

    public void viewSchedule() {
        System.out.printf("| %-8s | %-9s | %-9s | %-9s | %-9s | %-9s | %-9s |%n", "", "MONDAY", "TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY");
        for (int i = 0; i < 20; i++) {
            System.out.printf("| %-8s | %-9s | %-9s | %-9s | %-9s | %-9s | %-9s |%n", schedule[i][0], schedule[i][1], schedule[i][2], schedule[i][3], schedule[i][4], schedule[i][5], schedule[i][6]);
        }
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
