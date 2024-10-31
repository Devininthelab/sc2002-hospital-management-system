package org.example.menu;

import org.example.entity.Patient;
import org.example.entity.User;
import java.util.Scanner;

public class PatientMenu implements Menu {
    public void displayMenu() {
        System.out.println("=====PATIENT MENU=====");
        System.out.println("1. View Medical Record\n2. Update Personal Information\n3. View Available Appointment Slots\n4. Schedule an Appointment\n5. Reschedule an Appointment\n6. Cancel an Appointment\n7. View Scheduled Appointments\n8. View Past Appointment Outcome Record\n9. Logout");
    }

    public void handleChoice(int choice, User user) {
        Patient patient = (Patient) user;  // Cast User to Patient
        switch (choice) {
            case 1:
                viewMedicalRecord(patient);
                break;
            case 2:
                updatePersonalInformation(patient);
                break;
            case 3:
                viewAvailableAppointmentSlots(patient);
                break;
            case 4:
                scheduleAppointment(patient);
                break;
            case 5:
                rescheduleAppointment(patient);
                break;
            case 6:
                cancelAppointment(patient);
                break;
            case 7:
                viewScheduledAppointment(patient);
                break;
            case 8:
                viewPastAOR(patient);
                break;
            case 9:
                logout(patient);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void start(User user) {
        Patient patient = (Patient) user;  // Cast User to Patient
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            handleChoice(choice, patient);
        } while (choice != 9);  // Exit when logout is chosen
    }

    private void viewMedicalRecord(Patient patient) {}
    private void updatePersonalInformation(Patient patient) {
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
                    int dateOfBirth = sc.nextInt();
                    patient.setDateOfBirth(dateOfBirth);
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
                case 4:
                    System.out.println("Choose Blood Type:\n1. A+\n2. A-\n3. B+\n4. B-\n5. AB+\n6. AB-\n7. O+\n8. O-\n");
                    int bloodType = sc.nextInt();
                    switch (bloodType) {
                        case 1:
                            patient.setBloodType(Patient.BloodType.Apos);
                            break;
                        case 2:
                            patient.setBloodType(Patient.BloodType.Aneg);
                            break;
                        case 3:
                            patient.setBloodType(Patient.BloodType.Bpos);
                            break;
                        case 4:
                            patient.setBloodType(Patient.BloodType.Bneg);
                            break;
                        case 5:
                            patient.setBloodType(Patient.BloodType.ABpos);
                            break;
                        case 6:
                            patient.setBloodType(Patient.BloodType.ABneg);
                            break;
                        case 7:
                            patient.setBloodType(Patient.BloodType.Opos);
                            break;
                        case 8:
                            patient.setBloodType(Patient.BloodType.Oneg);
                            break;
                    }
                    break;
            }
            System.out.println("Choose information to update: \n1. Name\n2. Date of Birth\n3. Gender\n4. Blood Type");
            choice = sc.nextInt();
        }
        while (choice >= 1 && choice <= 8);
    }
}
// ENUM VALUES IN GENDER AND BLOOD TYPE SHOULD BE IN PUBLIC SCOPE
private void viewAvailableAppointmentSlots(Patient patient) {
    //implement method in Patient class
}
private void scheduleAppointment(Patient patient) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Timeslot:\n");
    int timeslot = sc.nextInt();
    patient.scheduleAppointment(timeslot);
}
private void rescheduleAppointment(Patient patient) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Timeslot:\n");
    int timeslot = sc.nextInt();
    patient.reScheduleAppointment();
}
private void cancelAppointment(Patient patient) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Timeslot:\n");
    int timeslot = sc.nextInt();
    patient.cancelAppointment(timeslot);
}

private void viewScheduledAppointment(Patient patient) {

}
private void viewPastAOR(Patient patient) {
    patient.viewPastAOR(); //
}
private void logout(Patient patient) {}

}

