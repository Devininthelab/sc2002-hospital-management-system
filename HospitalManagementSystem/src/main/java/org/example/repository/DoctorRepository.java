package org.example.repository;


import org.example.entity.Doctor;
import org.example.utils.DateToNumber;
import java.io.*;
import java.util.List;

/**
 * DoctorRepository is responsible for managing the schedule of a doctor
 * It reads from the doctor_availability database and
 */
public class DoctorRepository {
    private List<Doctor> doctors;
    private final StaffRepository staffRepository = new StaffRepository();
    public DoctorRepository() {
        doctors = staffRepository.getAllDoctors();
        for (Doctor doctor : doctors) {
            doctor.setSchedule(loadDoctorSchedule(doctor.getId()));
        }
    }

    /**
     * Load the doctor's schedule from the database
     * Each doctor has a separate file named {doctorId}_availability.csv
     * @param doctorId the doctor's ID
     * @return a 2D array of the doctor's schedule
     */
    public String[][] loadDoctorSchedule(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        String[][] schedule = new String[20][7];
        try (BufferedReader br = new BufferedReader(new FileReader(doctorId + "_availability.csv"))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                schedule[i] = data;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schedule;
    }

    public Doctor getDoctorById(String id) {
        return doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    /**
     * Update the doctor's schedule in the database.
     * Use function dateToNumber to convert date to number (read the function's documentation)
     * @param doctorId the doctor's ID
     * @param date the date of the appointment
     * @param time the time slot of the appointment
     * @param newStatus the new status of the appointment (e.g. "Available", "Booked", "Unavailable")
     */
    public void updateDoctorSchedule(String doctorId, String date, int time, String newStatus) {
        Doctor doctor = getDoctorById(doctorId);
        int number = DateToNumber.dateToNumber(date);
        doctor.getSchedule()[number][time] = newStatus;
        saveDoctorSchedule(doctorId);
    }

    public void freeDoctorSchedule(String doctorId, String date, int time) {
        updateDoctorSchedule(doctorId, date, time, "available");
    }

    public void saveDoctorSchedule(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        String[][] schedule = doctor.getSchedule();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(doctor.getId() + "_availability.csv"))) {
            for (int i = 0; i < schedule.length; i++) {
                bw.write(String.join(",", schedule[i]) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
