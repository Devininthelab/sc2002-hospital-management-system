package org.example.repository;

import org.example.entity.Appointment;
import org.example.entity.Staff;

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
        InputStream inputStream;
        boolean loadedFromResources = false;
        try {
            File writableFile = new File(filePath);
            if (writableFile.exists()) {
                inputStream = new FileInputStream(filePath);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(new File(filePath).getName());
                if (inputStream == null) {
                    System.err.println("File not found: " + filePath);
                    return;
                }
                loadedFromResources = true;
            }

            String line;
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //TEMPORARILY, TYPE OF DATE IS STRING, SO DON'T NEED FORMATTER HERE
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String header = br.readLine(); // Skip the header
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length >= 6) {
                        int id = Integer.parseInt(values[0]);
                        String doctorId = values[1].trim();
                        String patientId = values[2].trim();
                        String date = values[3].trim();
                        int timeslot = Integer.parseInt(values[4].trim());
                        String status = values[5].trim().toUpperCase();

                        Appointment appointment = new Appointment(id, patientId, doctorId, date, timeslot, status);
                        appointments.add(appointment);
                    }
                }
            }

            if (loadedFromResources) {
                saveAppointmentsToCSV();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            return;
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error parsing appointment data: " + e.getMessage());
        }

        //String line;
        ////DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ////TEMPORARILY, TYPE OF DATE IS STRING, SO DON'T NEED FORMATTER HERE
        //try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        //    String header = br.readLine(); // Skip the header
        //    while ((line = br.readLine()) != null) {
        //        String[] values = line.split(",");
        //        if (values.length >= 6) {
        //            int id = Integer.parseInt(values[0]);
        //            String doctorId = values[1].trim();
        //            String patientId = values[2].trim();
        //            String date = values[3].trim();
        //            int timeslot = Integer.parseInt(values[4].trim());
        //            String status = values[5].trim().toUpperCase();
        //
        //            Appointment appointment = new Appointment(id, patientId, doctorId, date, timeslot, status);
        //            appointments.add(appointment);
        //        }
        //    }
        //} catch (IOException e) {
        //    System.out.println("Error reading CSV file: " + e.getMessage());
        //} catch (Exception e) {
        //    System.out.println("Error parsing appointment data: " + e.getMessage());
        //}
    }

    /**
     * Save appointments to CSV file
     */
    public void saveAppointmentsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("appointmentId,doctorId,patientId,date,timeslot,status");
            bw.newLine();
            for (Appointment appointment : appointments) {
                bw.write(appointment.getId() + "," +
                        appointment.getDoctorId() + "," +
                        appointment.getPatientId() + "," +
                        appointment.getDate() + "," +
                        appointment.getTimeslot() + "," +
                        appointment.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
     * @param timeslot - from 0 to 7
     * @param status
     * @return
     */
    public void addAppointment(String patientId, String doctorId, String date, int timeslot, String status) {
        // Use counter to assign a unique ID
        Appointment appointment = new Appointment(counter++, patientId, doctorId, date, timeslot, "REQUESTED");
        appointments.add(appointment);
        saveAppointmentsToCSV();
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

    /**
     * Once accepted, an appointment cannot be rescheduled
     * @param id
     * @param date - from Monday to Saturday
     * @param timeslot - from 0 to 7
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
     * Get all appointments for a specific doctor
     * @param doctorId
     * @return
     */
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctorId().equals(doctorId))
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