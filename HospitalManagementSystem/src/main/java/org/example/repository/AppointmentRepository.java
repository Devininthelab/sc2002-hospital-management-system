package org.example.repository;

import org.example.entity.Appointment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AppointmentRepository
 * Depend on
 * - DoctorRepo to update their availability
 */
public class AppointmentRepository {
    private List<Appointment> appointments;
    private static int counter = 0;
    private final String filePath;

    public AppointmentRepository(String filePath) {
        this.filePath = filePath;
        appointments = new ArrayList<>();
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

    /**
     * Load appointments from CSV file
     */
    public void loadAppointmentsFromCSV() {
        String line;
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //TEMPORARILY, TYPE OF DATE IS STRING, SO DON'T NEED FORMATTER HERE
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    int id = Integer.parseInt(values[0]);
                    String patientId = values[1].trim();
                    String doctorId = values[2].trim();
                    String date = values[3].trim();
                    int timeslot = Integer.parseInt(values[4].trim());
                    String status = values[5].trim().toUpperCase();

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

    /**
     * Save appointments to CSV file
     */
    public void saveAppointmentsToCSV() {
        try (FileWriter writer = new FileWriter(filePath)) {
            // If the file is empty, write the header
            writer.write("appointmentId,doctorId,patientId,date,timeslot,status\n");

            for (Appointment appointment : appointments) {
                writer.write(appointment.getId() + "," +
                        appointment.getPatientId() + "," +
                        appointment.getDoctorId() + "," +
                        appointment.getDate() + "," +
                        appointment.getTimeslot() + "," +
                        appointment.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Get all appointments
     * @return a list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     * Add an appointment to the Appointment List
     * @param patientId
     * @param doctorId
     * @param date
     * @param timeslot
     * @param status
     * @return
     */
    public boolean addAppointment(String patientId, String doctorId, String date, int timeslot, String status) {
        // Use counter to assign a unique ID
        Appointment appointment = new Appointment(counter++, patientId, doctorId, date, timeslot, "PENDING");
        appointments.add(appointment);
        saveAppointmentsToCSV();
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
        appointment.reschedule(doctorId, date, timeslot);
        saveAppointmentsToCSV();
    }

    /**
     * Delete appointment from Appointment List
     * @param id
     */
    public void deleteAppointmentById(int id) {
        Appointment appointment = getAppointmentById(id);
        appointments.remove(appointment);
        saveAppointmentsToCSV();
    }

    /**
     * Get all appointments for a specific patient
     * @param patientId
     * @return
     */
    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    /**
     * Mark an appointment as completed
     * @param id
     */
    public void markAsCompleted(int id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus("COMPLETED");
        saveAppointmentsToCSV();
    }

    /**
     * Update the status of an appointment, after udate new status, save to CSV
     */
    public void updateAppointmentStatus(int id, String status){
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        saveAppointmentsToCSV();
    }
}