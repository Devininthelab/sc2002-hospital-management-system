package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorMenu implements Menu {
    private Scanner scanner;
    private Doctor doctor;
    private PatientRepository patientRepository;
    private StaffRepository staffRepository;
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private MedicationRepository medicationRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;

    public DoctorMenu(Scanner scanner, PatientRepository patientRepository, StaffRepository staffRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, MedicationRepository medicationRepository, AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository) {
        this.scanner = scanner;
        this.patientRepository = patientRepository;
        this.staffRepository = staffRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicationRepository = medicationRepository;
        this.appointmentOutcomeRecordRepository = appointmentOutcomeRecordRepository;
    }

    public void displayMenu() {
        System.out.println("=====DOCTOR MENU=====");
        System.out.println("1. View Patient Medical Records\n" +
                "2. Update Patient Medical Records\n" +
                "3. View Personal Schedule\n" +
                "4. Set Availability for Appointments\n" +
                "5. Manage Appointment Requests\n" +
                "6. View Upcoming Appointments\n" +
                "7. Complete an appointment\n" +
                "8. Change password" +
                "9. Logout");
    }

    public void start() {

        login();
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            handleChoice(choice);
        } while (choice != 8);  // Exit when logout is chosen
    }

    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            String id = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            doctor = doctorRepository.getDoctorById(id);
            if (doctor == null) {
                System.out.println("Doctor not found. Try again");
                continue;
            }

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
        System.out.println("Patient Medical Records");
        System.out.println(patient.medicalRecord());
    }

    public void updatePatientMedicalRecords() {
        System.out.println("Enter patient's id: ");

        String patientId = scanner.nextLine();

        Patient patient = patientRepository.getPatientById(patientId);
        System.out.println("Add new ");
        //TODO: implement update medical record field in PatientRepository

    }

    public void viewPersonalSchedule() {
        System.out.println("Your personal schedule:");
        doctor.printSchedule();
    }

    public void setAvailabilityForAppointments() {
        System.out.println("Choose a date and timeslot:");
        System.out.print("Date (Monday to Sunday): ");
        String date = scanner.nextLine();
        System.out.print("Timeslot: ");
        int timeslot = scanner.nextInt();
        System.out.print("New availability status (Available, Busy, Booked): ");
        String availability = scanner.nextLine();
        doctorRepository.updateDoctorSchedule(doctor.getId(), date, timeslot, availability);
    }

    public void manageAppointmentRequests() {
        List<Appointment> appointments = doctor.getAppointments();
        System.out.printf("You have %d appointment requests%n", appointments.size());

        System.out.printf("| | | | | | ");
        //TODO: how to differentiate requested from accepted appointed
    }

    public void viewUpcomingAppointments() {
        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.getStatus().equals("Accepted")) {
                System.out.println(appointment);
            }
        }
    }

    /**
     * Used to mark an appointment as completed. Doctor must fill out the outcome record
     * before it can be marked as completed
     * Input: Appointment id, date of completion, type of service
     */
    public void completeAppointment() {
        System.out.println("Mark appointment as completed");
        System.out.print("Enter appointment's id: ");
        int appointmentId = scanner.nextInt();
        // mark appointment as complete
        appointmentRepository.markAsCompleted(appointmentId);

        System.out.print("Enter date(dd/MM/yyyy): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        List<String> services = new ArrayList<>();
        while (true) {
            System.out.print("Enter type of service (leave empty to finish): ");
            String service = scanner.nextLine();
            if (service.equals("")) {
                break;
            }

            services.add(service);
        }
        //TODO: prompt for list of prescription
        List<Medication> prescribeMedications = new ArrayList<>();
        while (true) {
            System.out.print("Enter medications (leave empty to finish): ");
            String medicationName = scanner.nextLine();
            if (medicationName.equals("")) {
                break;
            }

            int quantity = scanner.nextInt();
            medicationRepository.addMedication(new Medication(appointmentId, medicationName, quantity));
        }

        System.out.print("Consultation Notes: ");
        String notes = scanner.nextLine();
        //TODO: appointment outcome record repository to save new outcome record
        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(appointmentId, date, notes, services, prescribeMedications);
        appointmentOutcomeRecordRepository.addAppointmentOutcomeRecord(record);
    }

    public void logout() {
        System.out.println("Logging out...");
    }
}
