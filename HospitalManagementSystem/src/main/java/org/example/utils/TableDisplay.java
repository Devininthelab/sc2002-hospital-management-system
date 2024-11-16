package org.example.utils;

import org.example.entity.Appointment;

import java.util.List;

public class TableDisplay {
    public static void printTimeslotOption() {
        // Print header for timeslot options
        System.out.println("\n=====================================");
        System.out.println("|        Timeslot Options           |");
        System.out.println("=====================================");

        // Print the transposed timeslot table
        System.out.printf("| %-10s | %-20s |%n", "Timeslot", "Time");
        System.out.println("|------------|----------------------|\n");

        System.out.printf("| %-10s | %-20s |%n", 1, "9:00-10:00");
        System.out.printf("| %-10s | %-20s |%n", 2, "10:00-11:00");
        System.out.printf("| %-10s | %-20s |%n", 3, "11:00-12:00");
        System.out.printf("| %-10s | %-20s |%n", 4, "12:00-13:00");
        System.out.printf("| %-10s | %-20s |%n", 5, "13:00-14:00");
        System.out.printf("| %-10s | %-20s |%n", 6, "14:00-15:00");
        System.out.printf("| %-10s | %-20s |%n", 7, "15:00-16:00");
        System.out.printf("| %-10s | %-20s |%n", 8, "16:00-17:00");

        System.out.println("=====================================");
    }

    public static void printAppointmentTable(List<Appointment> appointments) {
        // Print header for the appointment table
        System.out.println("\n================================================================================");
        System.out.println("|                              Appointment Table                               |");
        System.out.println("================================================================================");

        // Print the column headers
        System.out.printf("| %-6s | %-12s | %-12s | %-12s | %-15s | %-10s |%n",
                "ID", "Patient ID", "Doctor ID", "Date", "Timeslot", "Status");
        System.out.println("|--------|--------------|--------------|--------------|-----------------|------------|");

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
        System.out.println("================================================================================\n");
    }

    public static void printSchedule(String[][] schedule) {
        // Print table header
        System.out.println("\n=================================================================================================================");
        System.out.printf("| %-8s | %-11s | %-11s | %-11s | %-11s | %-11s | %-11s |%n", "Timeslot", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");
        System.out.println("=================================================================================================================");

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
        System.out.println("=================================================================================================================");
    }
}
