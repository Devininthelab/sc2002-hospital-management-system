package org.example.menu;


import org.example.entity.Appointment;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.repository.AppointmentOutcomeRecordRepository;
import org.example.repository.AppointmentRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;

import java.time.LocalDate;
import java.util.*;

public class PatientMenu implements Menu {

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;
    //TODO: change to id to maintain encapsulation
    private Patient patient;
    private Scanner scanner;

    public PatientMenu(PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository, Scanner scanner) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentOutcomeRecordRepository = appointmentOutcomeRecordRepository;
        this.scanner = scanner;
    }

    /**
     * Start the user menu, should run first when the program starts
     * Patient should log in first before accessing the menu
     */
    public void start() {
        login();
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            handleChoice(choice);
            System.out.print("Press enter to continue...");
            scanner.nextLine(); // Consume the newline character
        } while (choice != 10);  // Exit when logout is chosen
    }

    /**
     * Log in to the system, a patient should enter their id and password UNTIL they are correct
     * Patient state is stored in this phase
     */
    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            String id = scanner.nextLine();
            patient = patientRepository.getPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found. Try again");
                continue;
            }

            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            if (patient.getPassword().equals(password)) {
                System.out.println("You are logged in.");
                break;
            }

            System.out.println("Wrong password. Try again");
        }
    }

    /**
     * Display the menu options for the patient
     */
    public void displayMenu() {
        System.out.println("==============PATIENT MENU==============");
        System.out.println("1. Change password\n" +
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

    /**
     * Handle the choice of the patient
     * Wire the choice to the corresponding method
     * @param choice the choice of the patient
     */
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

    /**
     * Change the password of the patient
     * Patient should enter the new password
     * Call the patientRepository to update the password
     */
    private void changePassword() {
        System.out.print("Please enter new password: \n");
        String password = scanner.nextLine();
        patientRepository.updatePatientField(patient.getId(), "password",password);
        System.out.println("Password changed successfully.");
    }

    /**
     * View the medical record of the patient
     * Call the medicalRecord method in the patient class
     * Printing format: id, name, date of birth, gender, contact, blood type
     * Past diagnosis, treatment and prescription
     */
    private void viewMedicalRecord() {
        System.out.println("Patient Medical Record:");
        System.out.println("ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact: " + patient.getContact());
        System.out.println("Blood Type: " + patient.getBloodType());
        String diagnoses = String.join(", ", patient.getDiagnoses());
        String treatments = String.join(", ", patient.getTreatments());
        String prescriptions = String.join(", ", patient.getPrescriptions());

        System.out.println("Diagnoses: " + diagnoses);
        System.out.println("Treatments: " + treatments);
        System.out.println("Prescriptions: " + prescriptions);
    }

    /**
     * Update the personal information of the patient
     * Patient should be able to update their name, date of birth, and contact
     * Call the patientRepository to update the patient's information
     */
    private void updatePersonalInformation() {
        System.out.println("You can only update: \n" +
                "1. Name\n" +
                "2. Date of Birth\n" +
                "3. Contact\n" +
                "4. Gender\n" +
                "5. Finish");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        // Loop until the user chooses to finish
        do {
            switch (choice) {
                case 1:
                    System.out.println("Please enter your name: ");
                    sc.nextLine(); // Consume the newline character
                    String name = sc.nextLine(); // Read full name (including spaces)
                    patientRepository.updatePatientField(patient.getId(), "name", name);
                    System.out.println("Name updated successfully.");
                    break;
                case 2:
                    System.out.println("Please enter your date of birth (dd/MM/yyyy): ");
                    String dateOfBirth = sc.next();
                    patientRepository.updatePatientField(patient.getId(), "dateOfBirth", dateOfBirth);
                    System.out.println("Date of birth updated successfully.");
                    break;
                case 3:
                    System.out.println("Please enter your contact: ");
                    sc.nextLine(); // Consume the newline character
                    String contact = sc.nextLine(); // Read full contact number (including spaces if needed)
                    patientRepository.updatePatientField(patient.getId(), "contact", contact);
                    System.out.println("Contact updated successfully.");
                    break;
                case 4:
                    System.out.println("Please enter your gender: ");
                    String gender = sc.next();
                    patientRepository.updatePatientField(patient.getId(), "gender", gender);
                    System.out.println("Gender updated successfully.");
                    break;
                case 5:
                    System.out.println("All done successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            // Prompt user for the next action
            if (choice != 5) {
                System.out.println("Choose information to update: \n1. Name\n2. Date of Birth\n3. Contact\n4. Gender\n5. Finish");
                choice = sc.nextInt();
            }
        } while (choice != 5);
    }

    /**
     * View available appointment slots for a doctor
     * Patient should select a doctor to view their available appointment slots
     * (Use doctorRepository to interact with the doctor data)
     * Then print it the doctor's schedule
     * TODO: Consider moving the display of doctor's schedule to the Doctor class
     */
    private void viewAvailableAppointmentSlots() {
        List<Doctor> doctors = doctorRepository.getAllDoctors();
        System.out.println("------------------------------------");
        System.out.println("|           Doctor List            |");
        System.out.println("------------------------------------");
        System.out.printf("| %-8s | %-30s |%n", "ID", "NAME");

        for (Doctor doctor : doctors) {
            System.out.printf("| %-8s | %-30s |%n", doctor.getId(), doctor.getName());
        }

        Doctor doctor = null;
        while (doctor == null) {
            System.out.print("Select a doctor, provide doctor's id: ");
            String doctorId = scanner.nextLine();
            doctor = doctorRepository.getDoctorById(doctorId);

            if (doctor == null) {
                System.out.println("Doctor not found. Please try again.");
            }
        }

        String[][] schedule = doctor.getSchedule();
        doctor.printSchedule();
    }

    /**
     * Schedule an appointment with a doctor
     * Patient should select a doctor, date, and timeslot to schedule an appointment
     * (Use appointmentRepository to interact with the appointment data)
     */
    private void scheduleAppointment() {
        while (true) {
            System.out.print("Date (Monday to Saturday): ");
            String date = scanner.nextLine();
            System.out.print("Timeslot (1 to 8): ");
            int timeslot = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Select doctor ID: ");
            String doctorId = scanner.nextLine();
            // check if doctor is available
            if (doctorRepository.doctorIsAvailable(doctorId, date, timeslot)) {
                appointmentRepository.addAppointment(patient.getId(), doctorId, date, timeslot, "REQUESTED");
                doctorRepository.updateDoctorSchedule(doctorId, date, timeslot, "BOOKED");
                System.out.println("Doctor is available, Appointment requested");
                break;
            } else {
                System.out.println("Doctor is not available. Please choose another doctor or timeslot");
            }
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
        System.out.println("Current appointment detail:");
        System.out.println(appointment);

        // free up slot
        doctorRepository.freeDoctorSchedule(appointment.getDoctorId(), appointment.getDate(), appointment.getTimeslot());

        // reschedule input
        System.out.println("Change doctor? empty to keep same");
        String doctorId = scanner.nextLine();
        if (doctorId.isEmpty()) {
            doctorId = appointment.getDoctorId();
        }
        System.out.print("Date (Monday to Saturday): ");
        String date = scanner.nextLine();
        System.out.print("Timeslot (1 to 8): ");
        int timeslot = scanner.nextInt();

        // update database
        appointmentRepository.rescheduleAppointment(id, doctorId, date, timeslot);
        // occupy slot
        doctorRepository.updateDoctorSchedule(doctorId, date, timeslot, "BOOKED");
        System.out.println("Appointment rescheduled");
    }

    /**
     * Simply remove appointment using appointmentRepository
     */
    private void cancelAppointment() {
        System.out.println("Cancelling appointment");
        System.out.println("Enter appointment ID: ");
        int id = scanner.nextInt();
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        String doctorId = appointment.getDoctorId();
        String date = appointment.getDate();
        int timeslot = appointment.getTimeslot();
        appointmentRepository.deleteAppointmentById(id);
        doctorRepository.updateDoctorSchedule(doctorId, date, timeslot, "AVAILABLE");
        System.out.println("Appointment cancelled");
    }

    public void printAppointmentTable(List<Appointment> appointments) {
        System.out.printf("| %-4s | %-10s | %-10s | %-10s | %-10s | %-10s |%n",
                "ID", "Patient ID", "Doctor ID", "Date", "Timeslot", "Status");
        for (Appointment appointment : appointments) {
            System.out.printf("| %-4d | %-10s | %-10s | %-10s | %-10d | %-10s |%n",
                    appointment.getId(), appointment.getPatientId(), appointment.getDoctorId(),
                    appointment.getDate(), appointment.getTimeslot(), appointment.getStatus());
        }
    }

    /**
     * View the scheduled appointment of the patient
     * Display the detail and status of the appointment
     * TODO: agree on the format of the appointment detail, prefer table format
     */
    private void viewScheduledAppointment() {
        // detail and status of scheduled appointment
        List<Appointment> appointments = appointmentRepository.getAppointmentsByPatientId(patient.getId());
        System.out.println("Pending Appointment:");
        printAppointmentTable(appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("REQUESTED")).toList());
        System.out.println("Accepted Appointment:");
        printAppointmentTable(appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("ACCEPTED")).toList());
        System.out.println("Rejected Appointment (please reschedule or cancel):");
        printAppointmentTable(appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("REJECTED")).toList());
    }

    /**
     * View the past appointment outcome record of the patient
     * Display the outcome record of the past appointment
     */
    private void viewPastAppointmentOutcomeRecord() {
        System.out.println("Past Appointment Outcome Record:");
        List<AppointmentOutcomeRecord> records = new ArrayList<>();
        for (Appointment appointment : appointmentRepository.getAppointmentsByPatientId(patient.getId())) {
            records.add(appointmentOutcomeRecordRepository.getRecordById(appointment.getId()));
        }
    }

}


