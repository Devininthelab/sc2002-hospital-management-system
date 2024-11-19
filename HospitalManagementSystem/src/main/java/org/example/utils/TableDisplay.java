package org.example.utils;

import org.example.entity.Appointment;

import java.util.List;

/**
 * Utility class to display tables
 */
public class TableDisplay {
    /**
     * Print the timeslot options
     */
    public static void printTimeslotOption() {
        // Print header for timeslot options
        System.out.println("+------------+----------------------+");
        System.out.printf("| %-10s | %-20s |%n", "Timeslot", "Time");
        System.out.println("+------------+----------------------+");

        // Print the transposed timeslot table
        for (int i = 0; i < 8; i++) {
            System.out.printf("| %-10d | %-20s |%n", (i + 1), TimeslotToInt.timeslotToString(i));
        }

        System.out.println("+------------+----------------------+");
    }

    /**
     * Print the appointment table
     * @param tableName the name of the table
     * @param appointments the list of appointments
     */
    public static void printAppointmentTable(String tableName, List<Appointment> appointments) {
        int tableWidth = 84; // Width of the table border excluding the + and |
        int leftPadding = (tableWidth - tableName.length()) / 2;
        int rightPadding = tableWidth - leftPadding - tableName.length();
        System.out.println("\n+====================================================================================+");
        System.out.printf("|%s%s%s|%n", " ".repeat(leftPadding), tableName, " ".repeat(rightPadding));
        // Print table header
        System.out.println("+--------+--------------+--------------+--------------+-----------------+------------+");
        System.out.println("|   ID   | Patient ID   | Doctor ID    | Date         | Timeslot        | Status     |");
        System.out.println("+--------+--------------+--------------+--------------+-----------------+------------+");

        if (appointments.isEmpty()) {
            System.out.println("|                               No appointments found!                               |");
            System.out.println("+--------+--------------+--------------+--------------+-----------------+------------+");
            return;
        }

        // Print each appointment's details
        for (Appointment appointment : appointments) {
            System.out.printf("| %-6d | %-12s | %-12s | %-12s | %-15s | %-10s |%n",
                    appointment.getId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getDate(),
                    TimeslotToInt.timeslotToString(appointment.getTimeslot()),
                    appointment.getStatus());
        }

        // Print table footer
        System.out.println("+--------+--------------+--------------+--------------+-----------------+------------+");
    }

    /**
     * Print the schedule table
     * @param schedule the schedule to print
     */
    public static void printSchedule(String[][] schedule) {
        // Print table header
        System.out.println("\n+----------+-------------+-------------+-------------+-------------+-------------+-------------+");
        System.out.println("| Timeslot |    MONDAY   |   TUESDAY   |  WEDNESDAY  |  THURSDAY   |   FRIDAY    |  SATURDAY   |");
        System.out.println("+----------+-------------+-------------+-------------+-------------+-------------+-------------+");

        // Print each row of the schedule
        for (int i = 0; i < 8; i++) {
            // Print timeslot
            System.out.printf("| %-8s ", (9 + i) + ":00");

            // Print schedule details for each day
            for (int j = 0; j < 6; j++) {
                String cell = schedule[i][j] == null ? "" : schedule[i][j]; // Handle null values gracefully
                System.out.printf("| %-11s ", cell);
            }

            // End row
            System.out.println("|");
        }

        // Print table footer
        System.out.println("+----------+-------------+-------------+-------------+-------------+-------------+-------------+");
    }


}
