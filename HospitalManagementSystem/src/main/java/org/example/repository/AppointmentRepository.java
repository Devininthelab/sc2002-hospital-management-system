package org.example.repository;

import org.example.entity.Appointment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * AppointmentRepository
 * Depend on
 * - DoctorRepo to update their availability
 */
public class AppointmentRepository {
    private List<Appointment> appointments = new ArrayList<>();
    private static int counter = 0;

    private final String filePath = "src/main/resources/Appointment_List.csv";

    public AppointmentRepository() {
        loadAppointmentsFromCSV();
        setHighestAppointmentId();
    }

    /**
     * Auto increment the appointment ID counter to the highest ID in the list
     * meaning that the next appointment ID will be unique
     */

    private void setHighestAppointmentId() {
        int highestId = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getId() > highestId) {
                highestId = appointment.getId();
            }
        }
        counter = highestId + 1;  // Update counter to next available ID
    }



    public void loadAppointmentsFromCSV() {
        String line;
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //TEMPORARILY, TYPE OF DATE IS STRING, SO DON'T NEED FORMATTER HERE
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    int id = Integer.parseInt(values[0]);
                    String patientId = values[1].trim();
                    String doctorId = values[2].trim();
                    String date = values[3].trim();
                    int timeslot = Integer.parseInt(values[4].trim());
                    Appointment.Status status = Appointment.Status.valueOf(values[5].trim().toUpperCase());

                    Appointment appointment = new Appointment(id, patientId, doctorId, date, timeslot, status);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing appointment data: " + e.getMessage());
        }
    }

    public void saveAppointmentToCSV() {
        //TODO: rewrite entire csv
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public boolean addAppointment(String patientId, String doctorId, String date, int timeslot) {
        // Use counter to assign a unique ID
        Appointment appointment = new Appointment(counter++, patientId, doctorId, date, timeslot);
        appointments.add(appointment);
        saveAppointmentToCSV();
        System.out.println("Appointment added: " + appointment);
        return true;
    }

    public Appointment getAppointmentById(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null;  // Return null if appointment not found
    }

    /**
     * Once accepted, an appointment cannot be rescheduled
     * @param id
     * @param date
     * @param timeslot
     */
    public void rescheduleAppointment(int id, String doctorId, String date, int timeslot) {
        Appointment appointment = getAppointmentById(id);
        //TODO: change date and timeslot
        appointment.setDate(date);
        appointment.setTimeslot(timeslot);
        appointment.setDoctorID(doctorId);
        saveAppointmentToCSV();
    }



}