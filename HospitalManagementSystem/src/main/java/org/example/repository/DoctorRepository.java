package org.example.repository;


import org.example.entity.Doctor;
import org.example.utils.DateToNumber;
import org.example.utils.TimeslotToInt;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * DoctorRepository is responsible for managing the schedule of a doctor
 * It reads from the doctor_availability database and
 */
public class DoctorRepository {
    private List<Doctor> doctors;
    private String fileDir;

    /**
     * Constructor to create a DoctorRepository
     * @param fileDir - the writable path to the directory containing the doctor availability files
     * @param staffRepository - the StaffRepository containing the list of doctors
     */
    public DoctorRepository(String fileDir, StaffRepository staffRepository) {
        this.fileDir = fileDir;
        doctors = staffRepository.getAllDoctors();
        for (Doctor doctor : doctors) {
            doctor.setSchedule(loadDoctorSchedule(doctor.getId()));
        }
    }

    /**
     * Load the doctor's schedule from the database
     * Each doctor has a separated file named {doctorId}_availability.csv
     * If the file exists in the writable path, it will load from the file
     * If the file does not exist, it will load from the resources folder
     * @param doctorId the doctor's ID
     * @return a 2D array of the doctor's schedule
     */
    public String[][] loadDoctorSchedule(String doctorId) {
        InputStream inputStream;
        boolean loadedFromResources = false;
        String[][] schedule = new String[8][6];
        String filePath = fileDir + doctorId + "_availability.csv";

        try {
            File writableFile = new File(filePath);
            if (writableFile.exists()) {
                inputStream = new FileInputStream(filePath);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(Paths.get(fileDir).getFileName().toString() + "/" + new File(filePath).getName());
                if (inputStream == null) {
                    System.err.println("File not found: " + filePath);
                    return schedule;
                }
                loadedFromResources = true;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                int i = 0;
                String header = br.readLine(); // Skip the header
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    schedule[i] = new String[data.length - 1];
                    System.arraycopy(data, 1, schedule[i], 0, data.length - 1); // Skip the first entry in each row
                    i++;
                }
            }

            if (loadedFromResources) {
                saveDoctorSchedule(doctorId, schedule);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return schedule;
        } catch (IOException e) {
            e.printStackTrace();
            return schedule;
        }

        //String[][] schedule = new String[8][6];
        //try (BufferedReader br = new BufferedReader(new FileReader(fileDir + doctorId + "_availability.csv"))) {
        //    String line;
        //    int i = 0;
        //    String header = br.readLine(); // Skip the header
        //    while ((line = br.readLine()) != null) {
        //        String[] data = line.split(",");
        //        schedule[i] = new String[data.length - 1];
        //        System.arraycopy(data, 1, schedule[i], 0, data.length - 1); // Skip the first entry in each row
        //        i++;
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        return schedule;
    }

    /**
     * Get the doctor by ID
     * @param id the doctor's ID
     * @return the doctor with the specified ID
     */
    public Doctor getDoctorById(String id) {
        return doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all doctors
     * @return a list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    /**
     * Check if the doctor is available at the specified date and time
     * Use function dateToNumber to convert date to number (read the function's documentation)
     * @param doctorId the doctor's ID
     * @param date the date of the appointment
     * @param time the time slot of the appointment
     * @return true if the doctor is available, false otherwise
     */
    public boolean doctorIsAvailable(String doctorId, String date, int time) {
        Doctor doctor = getDoctorById(doctorId);
        int dayIndex = DateToNumber.dateToNumber(date);
        return "AVAILABLE".equalsIgnoreCase(doctor.getSchedule()[time][dayIndex]);
    }

    /**
     * Check if the doctor is booked at the specified date and time
     * Use function dateToNumber to convert date to number (read the function's documentation)
     * @param doctorId the doctor's ID
     * @param date the date of the appointment
     * @param time the time slot of the appointment
     * @return true if the doctor is booked, false otherwise
     */
    public boolean doctorBooked(String doctorId, String date, int time) {
        Doctor doctor = getDoctorById(doctorId);
        int dayIndex = DateToNumber.dateToNumber(date);
        return "BOOKED".equalsIgnoreCase(doctor.getSchedule()[time][dayIndex]);
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
        int dayIndex = DateToNumber.dateToNumber(date);
        doctor.getSchedule()[time][dayIndex] = newStatus.toUpperCase();
        saveDoctorSchedule(doctorId);
    }

    /**
     * Book a doctor's schedule
     * @param doctorId the doctor's ID
     * @param date the date of the appointment
     * @param time the time slot of the appointment from 0 to 7
     */
    public void freeDoctorSchedule(String doctorId, String date, int time) {
        updateDoctorSchedule(doctorId, date, time, "AVAILABLE");
    }

    /**
     * Save a doctor's schedule to the database
     * Only the doctor's id is required
     * Uses the overloaded method to save the schedule, for better consistency
     * @param doctorId the doctor's ID
     */
    public void saveDoctorSchedule(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        saveDoctorSchedule(doctorId, doctor.getSchedule());

        //try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileDir + doctorId + "_availability.csv"))) {
        //    // Write header row
        //    bw.write("TimeSlot,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday\n");
        //
        //    // Write each time slot with day-wise availability
        //    for (int i = 0; i < 8; i++) {
        //        bw.write(TimeslotToInt.timeslotToString(i) + "," + String.join(",", schedule[i]) + "\n");
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    /**
     * Save a doctor's schedule to the database
     * Provide the doctor's ID and the schedule to save
     * @param doctorId the doctor's ID
     * @param schedule the doctor's schedule
     */
    public void saveDoctorSchedule(String doctorId, String[][] schedule) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileDir + doctorId + "_availability.csv"))) {
            // Write header row
            bw.write("TimeSlot,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday\n");

            // Write each time slot with day-wise availability
            for (int i = 0; i < 8; i++) {
                bw.write(TimeslotToInt.timeslotToString(i) + "," + String.join(",", schedule[i]) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
