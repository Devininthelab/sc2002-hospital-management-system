package org.example.menu;

import org.example.entity.Doctor;

import java.util.Scanner;

public class DoctorMenu extends Menu {
    public void displayMenu() {
        System.out.println("=====DOCTOR MENU=====");
        System.out.println("1. View Patient Medical Records\n2. Update Patient Medical Records\n3. View Personal Schedule\n4. Set Availability for Appointments\n5. Accept or Decline Appointment Requests\n6. View Upcoming Appointments\n7. Record Appointment Outcome\n8. Logout");
    }

    public void handleChoice(int choice, User user) {
        Doctor doctor = (Doctor) user;  // Cast User to Doctor
        switch (choice) {
            case 1:
                viewPatientMedicalRecords(doctor);
                break;
            case 2:
                updatePatientMedicalRecords(doctor);
                break;
            case 3:
                viewPersonalSchedule(doctor);
                break;
            case 4:
                setAvailabilityForAppointments(doctor);
                break;
            case 5:
                acceptOrDeclineAppointmentRequests(doctor);
                break;
            case 6:
                viewUpcomingAppointments(doctor);
                break;
            case 7:
                recordAppointmentOutcome(doctor);
                break;
            case 8:
                logout(doctor);
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
            handleChoice(choice, doctor);
        } while (choice != 8);  // Exit when logout is chosen
    }
}
