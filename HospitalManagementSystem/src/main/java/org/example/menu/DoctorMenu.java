package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;
import org.example.utils.ChangePage;
import org.example.utils.TimeslotToInt;

import java.time.LocalDate;
import java.time.format.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.example.utils.TableDisplay.*;

public class DoctorMenu implements Menu {
    private Scanner scanner;
    private Doctor doctor;
    private PatientRepository patientRepository;
    private StaffRepository staffRepository;
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private PrescriptionRepository prescriptionRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;

    public DoctorMenu(Scanner scanner, PatientRepository patientRepository,
                      StaffRepository staffRepository, DoctorRepository doctorRepository,
                      AppointmentRepository appointmentRepository, PrescriptionRepository prescriptionRepository,
                      AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository) {
        this.scanner = scanner;
        this.patientRepository = patientRepository;
        this.staffRepository = staffRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentOutcomeRecordRepository = appointmentOutcomeRecordRepository;
    }

    public void displayMenu() {
        System.out.println("=============DOCTOR MENU=============");
        System.out.println("1. View Patient Medical Records\n" +
                "2. Update Patient Medical Records\n" +
                "3. View Personal Schedule\n" +
                "4. Set Availability for Appointments\n" +
                "5. Manage Appointment Requests\n" +
                "6. View Upcoming Appointments\n" +
                "7. Complete an appointment\n" +
                "8. Change password\n" +
                "9. Logout");
    }

    public void start() {

        login();
        int choice = 0;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            if (input.matches("[1-9]")) {  // Check if input is a single digit between 1 and 9
                choice = Integer.valueOf(input);
                handleChoice(choice);
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }
            System.out.println("Press Enter to continue...");
            scanner.nextLine();  // Wait for Enter key
            ChangePage.changePage();
        } while (choice != 9);  // Exit when logout is chosen
    }

    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            String id = scanner.nextLine();
            doctor = doctorRepository.getDoctorById(id);
            if (doctor == null) {
                System.out.println("Doctor not found. Try again");
                continue;
            }
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            if (doctor.getPassword().equals(password)) {
                System.out.println("You are logged in.");
                break;
            }

            System.out.println("Wrong password. Try again");
        }
    }

    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                viewPatientMedicalRecords();
                break;
            case 2:
                updatePatientMedicalRecords();
                break;
            case 3:
                viewPersonalSchedule();
                break;
            case 4:
                setAvailabilityForAppointments();
                break;
            case 5:
                manageAppointmentRequests();
                break;
            case 6:
                viewUpcomingAppointments();
                break;
            case 7:
                completeAppointment();
                break;
            case 8:
                changePassword();
                break;
            case 9:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void viewPatientMedicalRecords() {
        System.out.print("Enter patient's id: ");

        String patientId = scanner.nextLine();

        Patient patient = patientRepository.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.println("Patient Medical Records");
        System.out.println("Patient ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact: " + patient.getContact());
        System.out.println("Blood Type: " + patient.getBloodType());
        System.out.println("Diagnoses: ");
        for (String diagnosis : patient.getDiagnoses()) {
            System.out.println(" - " + diagnosis);
        }
        System.out.println("Prescriptions: ");
        for (String prescription : patient.getPrescriptions()) {
            System.out.println(" - " + prescription);
        }
        System.out.println("Treatment Plans: ");
        for (String treatment : patient.getTreatments()) {
            System.out.println(" - " + treatment);
        }
    }

    public void updatePatientMedicalRecords() {
        System.out.print("Enter patient's id: ");
        String patientId = scanner.nextLine();

        // Retrieve the patient by ID
        Patient patient = patientRepository.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        int choice;

        do {
            // Display update menu
            System.out.println("Enter the section you want to update (type 4 to exit):");
            System.out.println("1. Diagnoses");
            System.out.println("2. Treatment plans");
            System.out.println("3. Prescriptions");
            System.out.println("4. Exit");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Update Diagnoses
                    System.out.println("Type diagnosis to update (leave blank and press Enter to finish): ");
                    String diagnosis;
                    while (true) {
                        diagnosis = scanner.nextLine();
                        if (diagnosis.isEmpty()) {
                            break;
                        }
                        patient.addDiagnose(diagnosis);
                        System.out.println("Diagnosis added: " + diagnosis);
                    }

                    break;

                case 2:
                    // Update Treatment Plans
                    System.out.println("Type treatment plan to update (leave blank and press Enter to finish): ");
                    String treatmentPlan;
                    do {
                        treatmentPlan = scanner.nextLine();
                        if (!treatmentPlan.isEmpty()) {
                            patient.addTreatment(treatmentPlan);
                            System.out.println("Treatment plan added: " + treatmentPlan);
                        }
                    } while (!treatmentPlan.isEmpty());
                    break;
                case 3:
                    // Update Prescriptions
                    System.out.println("Type prescription to update (leave blank and press Enter to finish): ");
                    String prescription;
                    do {
                        prescription = scanner.nextLine();
                        if (!prescription.isEmpty()) {
                            patient.addPrescription(prescription);
                            System.out.println("Prescription added: " + prescription);
                        }
                    } while (!prescription.isEmpty());
                    break;

                case 4:
                    // Exit
                    System.out.println("Exiting update menu.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 4);

        // Save the updated patient information to CSV
        patientRepository.savePatientsToCSV();
        System.out.println("Patient medical records updated successfully.");
    }

    public void viewPersonalSchedule() {
        System.out.println("Your personal schedule:");
        printSchedule(doctor.getSchedule());
    }

    public void setAvailabilityForAppointments() {
        System.out.println("Choose a date and timeslot:");
        System.out.print("Date (Monday to Saturday): ");
        String date = scanner.nextLine();
        printTimeslotOption();
        System.out.print("Timeslot (1/9am to 8/4pm): ");
        int timeslot = Integer.valueOf(scanner.nextLine());
        System.out.print("New availability status (Available, Busy): ");
        String availability = scanner.nextLine();

        boolean success = doctorRepository.updateDoctorSchedule(doctor.getId(), date, timeslot - 1, availability);

        if (success) {
            System.out.println("Schedule updated successfully.");
        } else {
            System.out.println("Failed to update schedule. The timeslot is already BOOKED.");
        }
    }

    /**
     * Manage appointment requests for the doctor
     * Doctor can accept or reject appointment requests
     */
    public void manageAppointmentRequests() {
        System.out.println("===== Manage Appointment Requests =====");

        // Filter for only BOOKED schedules with REQUESTED status appointments
        System.out.println("Requested appointments:");
        List<Appointment> requestedAppointments = appointmentRepository.getAppointmentsByDoctorId(doctor.getId()).stream()
                .filter(appointment -> "REQUESTED".equals(appointment.getStatus()))
                .collect(Collectors.toList());

        //List<Appointment> requestedAppointments = doctor.getAppointments().stream()
        //        .filter(appointment -> "REQUESTED".equals(appointment.getStatus()))
        //        .collect(Collectors.toList());

        if (requestedAppointments.isEmpty()) {
            System.out.println("No appointment requests to manage.");
            return;
        }

        requestedAppointments.forEach(appointment -> {
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println(" - Patient ID: " + appointment.getPatientId());
            System.out.println(" - Date: " + appointment.getDate());
            System.out.println(" - Timeslot: " + TimeslotToInt.timeslotToString(appointment.getTimeslot()));
            System.out.println(" - Status: " + appointment.getStatus());

            System.out.print("Accept this appointment? (y/n/empty to ignore): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.isEmpty()) {
                return;
            }
            if ("y".equalsIgnoreCase(response)) {
                appointment.setStatus("ACCEPTED");
                System.out.println("Appointment accepted.");
            } else {
                appointment.setStatus("REJECTED");
                // Free the doctor's schedule
                doctorRepository.freeDoctorSchedule(doctor.getId(), appointment.getDate(), appointment.getTimeslot());
                System.out.println("Appointment rejected.");
            }

            // Update appointment status in the repository
            appointmentRepository.updateAppointmentStatus(appointment.getId(), appointment.getStatus());
        });

        System.out.println("Finished managing appointment requests.");
    }

    public void viewUpcomingAppointments() {
        List<Appointment> upcomingAppointments = appointmentRepository.getAppointmentsByDoctorId(doctor.getId()).stream()
                .filter(appointment -> "ACCEPTED".equals(appointment.getStatus()))
                .collect(Collectors.toList());
        printAppointmentTable("Upcoming appointments", upcomingAppointments);
    }

    /**
     * Used to mark an appointment as completed. Doctor must fill out the outcome record
     * before it can be marked as completed
     * Input: Appointment id, date of completion, type of service
     */
    public void completeAppointment() {
        System.out.println("Mark appointment as completed");
        System.out.print("Enter appointment's id: ");
        int appointmentId = Integer.valueOf(scanner.nextLine());

        // Check if the appointment exists
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        if (!appointment.getDoctorId().equals(doctor.getId())) {
            System.out.println("You don't have permission to complete this appointment.");
            return;
        }

        // Mark the appointment as complete
        appointmentRepository.markAsCompleted(appointmentId);
        // Free the doctor's schedule, no need to decrement timeslot
        doctorRepository.freeDoctorSchedule(doctor.getId(),
                appointmentRepository.getAppointmentById(appointmentId).getDate(),
                appointmentRepository.getAppointmentById(appointmentId).getTimeslot());

        // Prompt for date
        System.out.print("Enter date (dd/MM/yyyy): ");
        String date = scanner.nextLine();

        // Collect services provided during the appointment
        List<String> services = new ArrayList<>();
        while (true) {
            System.out.print("Enter type of service (leave empty to finish): ");
            String service = scanner.nextLine();
            if (service.isEmpty()) {
                break;
            }
            services.add(service);
        }

        List<Prescription> prescribePrescriptions = new ArrayList<>();
        Prescription prescriptionToAdd;
        while (true) {
            System.out.print("Enter prescription name (leave empty to finish): ");
            String prescriptionName = scanner.nextLine();
            if (prescriptionName.isEmpty()) {
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = Integer.valueOf(scanner.nextLine());

            prescriptionToAdd = new Prescription(appointmentId, prescriptionName, quantity);
            prescribePrescriptions.add(prescriptionToAdd);
            prescriptionRepository.addPrescription(prescriptionToAdd);
        }

        System.out.print("Consultation Notes: ");
        String notes = scanner.nextLine();

        // Create and save the outcome record
        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(appointmentId, date, notes, services, prescribePrescriptions);
        appointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(record);
    }

    public void changePassword() {
        while (true) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            if (newPassword.length() < 6) {
                System.out.println("Password must be at least 6 characters long.");
                continue;
            }
            doctor.setPassword(newPassword);
            staffRepository.updatePassword(doctor.getId(), newPassword);
            System.out.println("Password updated successfully.");
        }

    }

    public void logout() {
        System.out.println("Logging out...");
    }
}
