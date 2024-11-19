package org.example.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a doctor who is a type of staff member.
 * A doctor has a weekly schedule with 8 rows (hours per day)
 * and 6 columns (days of the week). Each time slot in the
 * schedule can have one of three statuses:
 * <ul>
 *     <li>AVAILABLE</li>
 *     <li>BUSY</li>
 *     <li>BOOKED</li>
 * </ul>
 */
public class Doctor extends Staff {
    private String[][] schedule;

    /**
     * Constructor for creating a new Doctor instance.
     * <p>
     * Initializes the schedule with a default size of 8 rows
     * and 6 columns, with all slots marked as "AVAILABLE".
     * </p>
     *
     * @param id       the unique identifier of the doctor
     * @param name     the name of the doctor
     * @param role     the role of the doctor (e.g., "Doctor")
     * @param gender   the gender of the doctor
     * @param age      the age of the doctor
     * @param password the password for the doctor's account
     */
    public Doctor(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);
        schedule = new String[8][6];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                schedule[i][j] = "AVAILABLE";
            }
        }
    }

    /**
     * Retrieves the schedule of the doctor.
     *
     * @return a 2D array representing the weekly schedule
     */
    public String[][] getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule for the doctor.
     *
     * @param schedule a 2D array representing the new schedule
     */
    public void setSchedule(String[][] schedule) {
        this.schedule = schedule;
    }
}
