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
    private String fileDir;
    
    public DoctorRepository(String fileDir, StaffRepository staffRepository) {
        this.fileDir = fileDir;
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
        String[][] schedule = new String[8][6];
        try (BufferedReader br = new BufferedReader(new FileReader(fileDir + doctorId + "_availability.csv"))) {
            String line;
            int i = 0;
            String header = br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                schedule[i] = new String[data.length - 1];
                System.arraycopy(data, 1, schedule[i], 0, data.length - 1); // Skip the first entry in each row
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
    public boolean updateDoctorSchedule(String doctorId, String date, int time, String newStatus) {
        Doctor doctor = getDoctorById(doctorId);
        int dayIndex = DateToNumber.dateToNumber(date);
        String currentStatus = doctor.getSchedule()[dayIndex][time];

        if ("BOOKED".equalsIgnoreCase(currentStatus)) {
            System.out.println("Cannot change the status of a BOOKED timeslot.");
            return false;
        }

        doctor.getSchedule()[dayIndex][time] = newStatus;
        saveDoctorSchedule(doctorId);
        return true;
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
