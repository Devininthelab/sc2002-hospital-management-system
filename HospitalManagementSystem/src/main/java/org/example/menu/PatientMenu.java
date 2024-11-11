package org.example.menu;


import org.example.entity.Appointment;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.repository.AppointmentOutcomeRecordRepository;
import org.example.repository.AppointmentRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class PatientMenu implements Menu {

    private final PatientRepository patientRepository = new PatientRepository();
    private final DoctorRepository doctorRepository = new DoctorRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository = new AppointmentOutcomeRecordRepository();
    private Patient patient;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Start the user menu, should run first when the program starts
     * Patient should log in first before accessing the menu
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        login();
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            handleChoice(choice);
        } while (choice != 9);  // Exit when logout is chosen
    }

    /**
     * Log in to the system, a patient should enter their id and password UNTIL they are correct
     * Patient state is stored in this phase
     */
    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            String id = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();
            patient = patientRepository.getPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found. Try again");
                continue;
            }

            if (patient.getPassword().equals(password)) {
                System.out.println("You are logged in.");
                break;
            }

            System.out.println("Wrong password. Try again");
        }
    }


    public void displayMenu() {
        System.out.println("=====PATIENT MENU=====");
        System.out.println("1. Change password" +
                "2. View Medical Record\n" +
                "3. Update Personal Information\n" +
                "4. View Available Appointment Slots\n" +
                "5. Schedule an Appointment\n" +
                "6. Reschedule an Appointment\n" +
                "7. Cancel an Appointment\n" +
                "8. View Scheduled Appointments\n" +
                "9. View Past Appointment Outcome Record\n" +
                "10. Logout");
    }

    public void handleChoice(int choice) {  // Cast User to Patient
        switch (choice) {
            case 1:
                changePassword();
                break;
            case 2:
                viewMedicalRecord();
                break;
            case 3:
                updatePersonalInformation();
                break;
            case 4:
                viewAvailableAppointmentSlots();
                break;
            case 5:
                scheduleAppointment();
                break;
            case 6:
                rescheduleAppointment();
                break;
            case 7:
                cancelAppointment();
                break;
            case 8:
                viewScheduledAppointment();
                break;
            case 9:
                viewPastAppointmentOutcomeRecord();
                break;
            case 10:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void changePassword() {
        System.out.print("Please enter new password: ");
        String password = scanner.nextLine();
        patientRepository.updatePatientField(patient.getId(), "password",password);
    }

    private void viewMedicalRecord() {
        System.out.println(patient.medicalRecord());
    }

    private void updatePersonalInformation() {
        System.out.println("You can only update: \n" +
                "1. Name\n" +
                "2. Date of Birth\n" +
                "3. Contact\n");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        do {
            switch (choice) {
                case 1:
                    String name = sc.next();
                    patient.setName(name);
                    break;
                case 2:
                    int day = sc.nextInt();
                    int month = sc.nextInt();
                    int year = sc.nextInt();
                    LocalDate birthDate = LocalDate.of(year, month, day);
                    patient.setDateOfBirth(birthDate);
                    break;
                case 3:
                    System.out.println("Choose gender:\n1. Male\n2. Female");
                    int gender = sc.nextInt();
                    if (gender == 1) {
                        patient.setGender("Male");
                    } else if (gender == 2) {
                        patient.setGender("Female");
                    }
                    break;
            }
            System.out.println("Choose information to update: \n1. Name\n2. Date of Birth\n3. Gender\n4. Blood Type");
            choice = sc.nextInt();
        }
        while (choice >= 1 && choice <= 8);
    }

    /**
     * View available appointment slots for a doctor
     * Patient should select a doctor to view their available appointment slots
     * (Use doctorRepository to interact with the doctor data)
     * Then prinit the doctor's schedule
     */
    private void viewAvailableAppointmentSlots() {
        //implement method in Patient class
        List<Doctor> doctors = doctorRepository.getAllDoctors();
        System.out.println("------------------------------------");
        System.out.println("|           Doctor List            |");
        System.out.println("------------------------------------");
        System.out.printf("| %-8s | %-30s |%n", "ID", "NAME");
        for (Doctor doctor : doctors) {
            System.out.printf("| %-8s | %-30s |%n", doctor.getId(), doctor.getName());
        }
        // print doctor's name, and id
        System.out.print("Select a doctor, provide doctor's id: ");
        String doctorId = scanner.nextLine();
        Doctor doctor = doctorRepository.getDoctorById(doctorId);
        String[][] schedule = doctor.getSchedule();
        System.out.printf("| %-8s | %-9s | %-9s | %-9s | %-9s | %-9s | %-9s |%n", "", "MONDAY", "TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY");
        for (int i = 0; i < 20; i++) {
            System.out.printf("| %-8s | %-9s | %-9s | %-9s | %-9s | %-9s | %-9s |%n", schedule[i][0], schedule[i][1], schedule[i][2], schedule[i][3], schedule[i][4], schedule[i][5], schedule[i][6]);
        }
    }

    /**
     * Schedule an appointment with a doctor
     * Patient should select a doctor, date, and timeslot to schedule an appointment
     * (Use appointmentRepository to interact with the appointment data)
     */
    private void scheduleAppointment() {
        System.out.print("Date(Monday to Sunday): ");
        String date = scanner.nextLine();
        System.out.print("Timeslot:");
        int timeslot = scanner.nextInt();
        System.out.print("Select doctor ID:");
        String doctorId = scanner.nextLine();
        boolean success = appointmentRepository.addAppointment(patient.getId(), doctorId, date, timeslot);
        if (success) {
            System.out.println("Appointment scheduled");
        } else {
            System.out.println("Something went wrong. Try again");
        }
    }

    /**
     * Reschedule an appointment
     * Uniquely identify appointment by id, reference by viewScheduledAppointment()
     * Give the choice to keep the same doctor
     * Give the choice to change date and timeslot
     * Update availability before and after rescheduling
     */
    private void rescheduleAppointment() {
        System.out.print("Select appointment ID:");
        int id = scanner.nextInt();
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        // free up slot
        doctorRepository.freeDoctorSchedule(appointment.getDoctorId(), appointment.getDate(), appointment.getTimeslot());
        System.out.println("Change doctor? empty to keep same");
        String doctorId = scanner.nextLine();
        if (doctorId.isEmpty()) {
            doctorId = appointment.getDoctorId();
        }
        System.out.print("Date(Monday to Sunday): ");
        String date = scanner.nextLine();
        System.out.print("Timeslot:");
        int timeslot = scanner.nextInt();
        appointmentRepository.rescheduleAppointment(id, doctorId, date, timeslot);
        // occupy slot
        doctorRepository.updateDoctorSchedule(doctorId, date, timeslot, "unavailable");
    }

    /**
     * Simply remove appointment using appointmentRepository
     */
    private void cancelAppointment() {
        System.out.println("Cancelling appointment");
        System.out.println("Enter appointment ID: ");
        int id = scanner.nextInt();
        appointmentRepository.deleteAppointmentById(id);
    }

    /**
     * Use appointmentRepository to
     */
    private void viewScheduledAppointment() {
        // detail and status of scheduled appointment
        List<Appointment> appointments = appointmentRepository.getAppointmentsByPatientId(patient.getId());
        System.out.println("Scheduled Appointment:");
        for (Appointment appointment : appointments.stream()
                .filter(appointment -> appointment.getStatus().equals())) {
            System.out.println(appointment);
        }
    }


    private void viewPastAppointmentOutcomeRecord() {
        System.out.println("Past Appointment Outcome Record:");
        List<AppointmentOutcomeRecord> records = new ArrayList<>();
        for (Appointment appointment : appointmentRepository.getAppointmentsByPatientId(patient.getId())) {
            records.add(appointmentOutcomeRecordRepository.getRecordById(appointment.getId()));
        }
    }
}


