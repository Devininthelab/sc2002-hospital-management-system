package org.example.menu;


import org.example.entity.Patient;
import org.example.repository.PatientRepository;

import java.time.LocalDate;
import java.util.Scanner;

public class PatientMenu implements Menu {

    private final PatientRepository patientRepository = new PatientRepository();
    private Patient patient;
    private Scanner scanner = new Scanner(System.in);

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

    public void login() {
        while (true) {
            System.out.print("Please enter your user id: ");
            int id = scanner.nextInt();
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

    // patient menu will be passed in an id
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
                viewPastAOR();
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
        System.out.println("Choose information to update: \n1. Name\n2. Date of Birth\n3. Gender\n4. Blood Type");
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
                        patient.setGender(Patient.Gender.MALE);
                    } else if (gender == 2) {
                        patient.setGender(Patient.Gender.FEMALE);
                    }
                    break;
            }
            System.out.println("Choose information to update: \n1. Name\n2. Date of Birth\n3. Gender\n4. Blood Type");
            choice = sc.nextInt();
        }
        while (choice >= 1 && choice <= 8);
    }

    // ENUM VALUES IN GENDER AND BLOOD TYPE SHOULD BE IN PUBLIC SCOPE
    private void viewAvailableAppointmentSlots() {
        //implement method in Patient class
    }


    private void scheduleAppointment() {

        System.out.print("Timeslot:");
        int timeslot = scanner.nextInt();
        System.out.print("Select doctor ID:");
        int doctorId = scanner.nextInt();
        patientRepository.scheduleAppointment();

        patientService.scheduleAppointment(doctorId, timeslot);
    }
    private void rescheduleAppointment() {
        System.out.print("Timeslot:");
        int timeslot = scanner.nextInt();
        System.out.print("Select doctor ID:");
        int doctorId = scanner.nextInt();
        patientService.reScheduleAppointment(doctorId, timeslot);
    }
    private void cancelAppointment() {
        System.out.print("Timeslot:");
        int timeslot = scanner.nextInt();
        System.out.print("Select doctor ID:");
        int doctorId = scanner.nextInt();
        patientService.cancelAppointment(doctorId, timeslot);
    }

    private void viewScheduledAppointment() {
        // detail and status of scheduled appointment
        System.out.println("Scheduled Appointment:");
    }
    private void viewPastAOR() {
        System.out.println("Past Appointment Outcome Record:");
    }
}


