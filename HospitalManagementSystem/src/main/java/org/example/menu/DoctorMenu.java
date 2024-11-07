package org.example.menu;

import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;
import org.example.repository.StaffRepository;

import java.util.Scanner;

public class DoctorMenu implements Menu {
    private Scanner scanner = new Scanner(System.in);
    private Doctor doctor;
    private final PatientRepository patientRepository = new PatientRepository();
    private final StaffRepository staffRepository = new StaffRepository();
    private DoctorRepository doctorRepository = new DoctorRepository();


    public void displayMenu() {
        System.out.println("=====DOCTOR MENU=====");
        System.out.println("1. View Patient Medical Records\n" +
                "2. Update Patient Medical Records\n" +
                "3. View Personal Schedule\n" +
                "4. Set Availability for Appointments\n" +
                "5. Accept or Decline Appointment Requests\n" +
                "6. View Upcoming Appointments\n" +
                "7. Record Appointment Outcome\n" +
                "8. Change password" +
                "9. Logout");
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
          // Cast User to Doctor
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
                acceptOrDeclineAppointmentRequests();
                break;
            case 6:
                viewUpcomingAppointments();
                break;
            case 7:
                recordAppointmentOutcome();
                break;
            case 8:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void start() {
        //Doctor doctor = (Doctor) user;  // Cast User to Doctor
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            handleChoice(choice);
        } while (choice != 8);  // Exit when logout is chosen
    }

    public void viewPatientMedicalRecords() {
        System.out.print("Enter patient's id: ");
        int patientId = scanner.nextInt();
        Patient patient = patientRepository.getPatientById(patientId);
        System.out.println("Patient Medical Records");
        System.out.println(patient.medicalRecord());
    }

    public void updatePatientMedicalRecords() {
        System.out.println("Enter patient's id: ");
        int patientId = scanner.nextInt();
        Patient patient = patientRepository.getPatientById(patientId);
        System.out.println("Add new ");

    }

    public void viewPersonalSchedule() {
        doctor.setSchedule(doctorRepository.loadDoctorSchedule(doctor.getId()));

    }

    public void setAvailabilityForAppointments() {

    }

    public void acceptOrDeclineAppointmentRequests() {

    }

    public void viewUpcomingAppointments() {

    }

    public void recordAppointmentOutcome() {

    }

    public void logout() {
        System.out.println("Logging out...");
    }
}
