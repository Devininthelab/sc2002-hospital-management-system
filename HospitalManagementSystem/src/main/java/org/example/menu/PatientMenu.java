package org.example.menu;


import org.example.entity.*;
import org.example.repository.AppointmentOutcomeRecordRepository;
import org.example.repository.AppointmentRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;
import org.example.utils.DateToNumber;
import org.example.utils.TimeslotToInt;

import java.time.LocalDate;
import java.util.*;

public class PatientMenu implements Menu {

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;
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
        int choice = 0;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (choice >= 1 && choice <= 10) {
                    handleChoice(choice);
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();  // Wait for Enter key
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 10.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
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
    public void handleChoice(int choice) {// Cast User to Patient
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
                break;
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
        System.out.println(" - ID: " + patient.getId());
        System.out.println(" - Name: " + patient.getName());
        System.out.println(" - Date of Birth: " + patient.getDateOfBirth());
        System.out.println(" - Gender: " + patient.getGender());
        System.out.println(" - Contact: " + patient.getContact());
        System.out.println(" - Blood Type: " + patient.getBloodType());
        String diagnoses = String.join(", ", patient.getDiagnoses());
        String treatments = String.join(", ", patient.getTreatments());
        String prescriptions = String.join(", ", patient.getPrescriptions());

        System.out.println(" - Diagnoses: " + diagnoses);
        System.out.println(" - Treatments: " + treatments);
        System.out.println(" - Prescriptions: " + prescriptions);
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

        int choice = Integer.valueOf(scanner.nextLine());

        // Loop until the user chooses to finish
        do {
            switch (choice) {
                case 1:
                    System.out.print("Please enter your name: ");
                    String name = scanner.nextLine(); // Read full name (including spaces)
                    patientRepository.updatePatientField(patient.getId(), "name", name);
                    System.out.println("Name updated successfully.");
                    break;
                case 2:
                    System.out.print("Please enter your date of birth (dd/MM/yyyy): ");
                    String dateOfBirth = scanner.nextLine();
                    patientRepository.updatePatientField(patient.getId(), "dateOfBirth", dateOfBirth);
                    System.out.println("Date of birth updated successfully.");
                    break;
                case 3:
                    System.out.println("Please enter your contact: ");
                    String contact = scanner.nextLine(); // Read full contact number (including spaces if needed)
                    patientRepository.updatePatientField(patient.getId(), "contact", contact);
                    System.out.println("Contact updated successfully.");
                    break;
                case 4:
                    System.out.println("Please enter your gender: ");
                    String gender = scanner.nextLine();
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
                choice = Integer.valueOf(scanner.nextLine());
            }
        } while (choice != 5);
    }

    /**
     * View available appointment slots for a doctor
     * Patient should select a doctor to view their available appointment slots
     * (Use doctorRepository to interact with the doctor data)
     * Then print it the doctor's schedule
     */
    private void viewAvailableAppointmentSlots() {
        List<Doctor> doctors = doctorRepository.getAllDoctors();
        System.out.println("---------------------------------------------");
        System.out.println("|                Doctor List                |");
        System.out.println("---------------------------------------------");
        System.out.printf("| %-8s | %-30s |%n", "ID", "NAME");
        System.out.println("|----------|--------------------------------|");
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
            int timeslot = Integer.valueOf(scanner.nextLine());
            System.out.print("Select doctor ID: ");
            String doctorId = scanner.nextLine();
            // check if doctor is available
            if (doctorRepository.doctorIsAvailable(doctorId, date, timeslot - 1)) {
                appointmentRepository.addAppointment(patient.getId(), doctorId, date,  timeslot - 1, "REQUESTED");
                doctorRepository.updateDoctorSchedule(doctorId, date, timeslot - 1, "BOOKED");
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
        int id = Integer.valueOf(scanner.nextLine());
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        if (appointment.getStatus().equals("ACCEPTED") || appointment.getStatus().equals("COMPLETED")) {
            System.out.println("Appointment has been accepted or completed, cannot reschedule");
            return;
        }
        System.out.println("Current appointment detail:");
        System.out.println(appointment);

        // free up slot, no need to decrement timeslot because it is already decremented in the appointmentRepository
        doctorRepository.freeDoctorSchedule(appointment.getDoctorId(), appointment.getDate(), appointment.getTimeslot());

        System.out.println("Reschedule appointment");
        System.out.println("Change doctor? empty to keep same");
        String doctorId;
        while (true) {
            doctorId = scanner.nextLine();
            if (doctorId.isEmpty()) {
                doctorId = appointment.getDoctorId();
                break;
            }

            if (doctorRepository.getDoctorById(doctorId) == null) {
                System.out.println("Doctor not found. Please try again.");
            } else {
                break;
            }
        }

        // reschedule input
        while (true) {
            // TODO: validate date and timeslot
            System.out.print("Date (Monday to Saturday): ");
            String date = scanner.nextLine();
            System.out.print("Timeslot (1 to 8): ");
            int timeslot = Integer.valueOf(scanner.nextLine());

            // check if doctor is available
            if (doctorRepository.doctorIsAvailable(doctorId, date, timeslot - 1)) {
                appointmentRepository.rescheduleAppointment(id, doctorId, date, timeslot - 1);
                doctorRepository.updateDoctorSchedule(doctorId, date, timeslot - 1, "BOOKED");
                System.out.println("Appointment rescheduled");
                break;
            } else {
                System.out.println("Doctor is not available. Please choose another doctor or timeslot");
            }
        }
    }

    /**
     * Simply remove appointment using appointmentRepository
     * Patient are allowed to cancel appointment even if it is accepted
     */
    private void cancelAppointment() {
        System.out.println("Cancelling appointment");
        System.out.println("Enter appointment ID: ");
        int id = Integer.valueOf(scanner.nextLine());
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        if (appointment == null) {
            System.out.println("Appointment not found");
            return;
        }

        if (appointment.getStatus().equals("COMPLETED")) {
            System.out.println("Appointment has been completed, cannot cancel");
            return;
        }
        String doctorId = appointment.getDoctorId();
        String date = appointment.getDate();
        int timeslot = appointment.getTimeslot();
        appointmentRepository.deleteAppointmentById(id);
        // no need to decrement timeslot because it is already decremented in the appointmentRepository
        doctorRepository.freeDoctorSchedule(doctorId, date, timeslot);
        System.out.println("Appointment cancelled");
    }

    public void printAppointmentTable(List<Appointment> appointments) {
        System.out.printf("| %-4s | %-10s | %-10s | %-10s | %-13s | %-10s |%n",
                "ID", "Patient ID", "Doctor ID", "Date", "Timeslot", "Status");
        System.out.println("|------|------------|------------|------------|---------------|------------|");
        for (Appointment appointment : appointments) {
            System.out.printf("| %-4d | %-10s | %-10s | %-10s | %-13s | %-10s |%n",
                    appointment.getId(), appointment.getPatientId(), appointment.getDoctorId(),
                    appointment.getDate(), TimeslotToInt.timeslotToString(appointment.getTimeslot()), appointment.getStatus());
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
        List<Appointment> appointments = appointmentRepository.getAppointmentsByPatientId(patient.getId());
        for (Appointment appointment : appointments) {
            records.add(appointmentOutcomeRecordRepository.getRecordById(appointment.getId()));
        }
        // filter null records
        records = records.stream().filter(Objects::nonNull).toList();

        for (AppointmentOutcomeRecord record : records) {
            // custom print
            System.out.println("Appointment ID: " + record.getAppointmentId());
            System.out.println(" - Date: " + record.getDate());
            System.out.println(" - Consultation Notes: " + record.getConsultationNotes());
            System.out.println(" - Services: " + String.join(", ", record.getTypeOfService()));
            System.out.println(" - Prescription: ");
            // table format
            System.out.printf("| %-10s | %-10s | %-10s |%n",
                    "Name", "Dosage", "Status");
            System.out.println("|------------|------------|------------|");
            for (Prescription prescription : record.getPrescriptions()) {
                System.out.printf("| %-10s | %-10s | %-10s |%n",
                        prescription.getName(), prescription.getQuantity(), prescription.getStatus());
            }
        }
    }

}


