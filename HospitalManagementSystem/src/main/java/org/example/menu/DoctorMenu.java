package org.example.menu;

import org.example.entity.*;
import org.example.repository.*;

import java.time.LocalDate;
import java.time.format.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        // Retrieve the patient by ID
        Patient patient = patientRepository.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            // Display update menu
            System.out.println("Enter the section you want to update (type 4 to exit):");
            System.out.println("1. Diagnoses");
            System.out.println("2. Prescriptions");
            System.out.println("3. Treatment plans");
            System.out.println("4. Exit");

            choice = sc.nextInt();
            sc.nextLine(); // Consume the leftover newline character

            switch (choice) {
                case 1:
                    // Update Diagnoses
                    System.out.println("Type diagnosis to update (leave blank and press Enter to finish): ");
                    String diagnosis;
                    do {
                        diagnosis = sc.nextLine();
                        if (!diagnosis.isEmpty()) {
                            patient.addDiagnose(diagnosis);
                            System.out.println("Diagnosis added: " + diagnosis);
                        }
                    } while (!diagnosis.isEmpty());
                    break;

                case 2:
                    // Update Prescriptions
                    System.out.println("Type prescription to update (leave blank and press Enter to finish): ");
                    String prescription;
                    do {
                        prescription = sc.nextLine();
                        if (!prescription.isEmpty()) {
                            patient.addPrescription(prescription);
                            System.out.println("Prescription added: " + prescription);
                        }
                    } while (!prescription.isEmpty());
                    break;

                case 3:
                    // Update Treatment Plans
                    System.out.println("Type treatment plan to update (leave blank and press Enter to finish): ");
                    String treatmentPlan;
                    do {
                        treatmentPlan = sc.nextLine();
                        if (!treatmentPlan.isEmpty()) {
                            patient.addTreatment(treatmentPlan);
                            System.out.println("Treatment plan added: " + treatmentPlan);
                        }
                    } while (!treatmentPlan.isEmpty());
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
        doctor.printSchedule();
    }

    public void setAvailabilityForAppointments() {
        System.out.println("Choose a date and timeslot:");
        System.out.print("Date (Monday to Sunday): ");
        String date = scanner.nextLine();
        System.out.print("Timeslot: ");
        int timeslot = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("New availability status (Available, Busy): ");
        String availability = scanner.nextLine();

        boolean success = doctorRepository.updateDoctorSchedule(doctor.getId(), date, timeslot, availability);

        if (success) {
            System.out.println("Schedule updated successfully.");
        } else {
            System.out.println("Failed to update schedule. The timeslot is already BOOKED.");
        }
    }

    public void manageAppointmentRequests() {
        System.out.println("===== Manage Appointment Requests =====");

        // Filter for only BOOKED schedules with REQUESTED status appointments
        List<Appointment> requestedAppointments = doctor.getAppointments().stream()
                .filter(appt -> "BOOKED".equals(appt.getStatus()) && "REQUESTED".equals(appt.getStatus()))
                .collect(Collectors.toList());

        if (requestedAppointments.isEmpty()) {
            System.out.println("No appointment requests to manage.");
            return;
        }

        requestedAppointments.forEach(appointment -> {
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("Patient ID: " + appointment.getPatientId());
            System.out.println("Date: " + appointment.getDate());
            System.out.println("Timeslot: " + appointment.getTimeslot());
            System.out.println("Status: " + appointment.getStatus());

            System.out.print("Accept this appointment? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if ("y".equals(response)) {
                appointment.setStatus("ACCEPTED");
                System.out.println("Appointment accepted.");
            } else {
                appointment.setStatus("REJECTED");
                System.out.println("Appointment rejected.");
            }

            // Update appointment status in the repository
            appointmentRepository.updateAppointmentStatus(appointment.getId(), appointment.getStatus());
        });

        System.out.println("Finished managing appointment requests.");
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
        scanner.nextLine(); // Consume newline

        // Mark the appointment as complete
        appointmentRepository.markAsCompleted(appointmentId);

        // Prompt for date
        System.out.print("Enter date (dd/MM/yyyy): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

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

        //TODO: prompt for list of prescription
        List<Prescription> prescribePrescriptions = new ArrayList<>();
        Prescription prescriptionToAdd;
        while (true) {
            System.out.print("Enter prescription name (leave empty to finish): ");
            String prescriptionName = scanner.nextLine();
            if (prescriptionName.isEmpty()) {
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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

    public void logout() {
        System.out.println("Logging out...");
    }
}
