package org.example.repository;

import org.example.entity.Appointment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    private List<Appointment> appointments = new ArrayList<>();
    private static final String filePath = "src/main/resources/Appointment.csv";
    //AVOID DUPLICATE ID WHEN WORKING WITH DATABASE AND COUNTER
    private void setHighestAppointmentId() {
        int highestId = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getId() > highestId) {
                highestId = appointment.getId();
            }
        }
        Appointment.setCounter(highestId);  // Set counter to the next available ID
    }

    public AppointmentRepository() {
        loadAppointmentsFromCSV();
        setHighestAppointmentId();
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

    public void saveAppointmentToCSV(Appointment appointment, String filePath) {
        //IMPLEMENT LATER
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        Appointment.setCounter(appointment.getId());
        saveAppointmentToCSV(appointment, filePath);
        System.out.println("Appointment added: " + appointment);
    }

    public Appointment getAppointmentById(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null;  // Return null if appointment not found
    }

    public void displayAppointments() {
        appointments.forEach(System.out::println);
    }
}