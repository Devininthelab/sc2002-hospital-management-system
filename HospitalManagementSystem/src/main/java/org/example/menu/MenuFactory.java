package org.example.menu;

import org.example.repository.*;

import java.util.Scanner;

/**
 * A factory class to create different menus
 * It also handles dependencies injection
 */
public class MenuFactory {
    private final Scanner scanner = new Scanner(System.in);

    private final AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository = new AppointmentOutcomeRecordRepository();
    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final DoctorRepository doctorRepository = new DoctorRepository();
    private final MedicineRepository medicineRepository = new MedicineRepository();
    private final MedicineRequestRepository medicineRequestRepository = new MedicineRequestRepository();
    private final PatientRepository patientRepository = new PatientRepository();
    private final PrescriptionRepository prescriptionRepository = new PrescriptionRepository();
    private final StaffRepository staffRepository = new StaffRepository();

    public Menu createMenu(String role) {
        switch (role) {
            case "patient":
                return new PatientMenu(patientRepository,
                        doctorRepository,
                        appointmentRepository,
                        appointmentOutcomeRecordRepository,
                        scanner);
            case "doctor":
                return new DoctorMenu(scanner,
                        patientRepository,
                        staffRepository,
                        doctorRepository,
                        appointmentRepository,
                        prescriptionRepository,
                        appointmentOutcomeRecordRepository);
            case "pharmacist":
                return new PharmacistMenu(scanner,
                        staffRepository,
                        medicineRepository,
                        appointmentOutcomeRecordRepository,
                        medicineRequestRepository,
                        prescriptionRepository);
            case "administrator":

                break;
        }
        return null;
    }

}
