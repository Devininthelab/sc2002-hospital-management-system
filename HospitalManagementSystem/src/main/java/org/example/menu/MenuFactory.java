package org.example.menu;

import org.example.repository.*;

import java.util.Scanner;

/**
 * A factory class to create different menus
 * It also handles dependencies injection
 */
public class MenuFactory {
    private final Scanner scanner = new Scanner(System.in);
    private final String appointmentOutcomeRecordPath = "src/main/resources/AppointmentOutcomeRecord.csv";
    private final String appointmentPath = "src/main/resources/Appointment_List.csv";
    private final String doctorDir = "src/main/resources/doctor_availability/";
    private final String medicinePath = "src/main/resources/Medicine_List.csv";
    private final String medicineRequestPath = "src/main/resources/Medicine_Requests.csv";
    private final String patientPath = "src/main/resources/Patient_List.csv";
    private final String prescriptionPath = "src/main/resources/Prescription.csv";
    private final String staffPath = "src/main/resources/Staff_List.csv";

    private final AppointmentRepository appointmentRepository = new AppointmentRepository(appointmentPath);
    private final MedicineRepository medicineRepository = new MedicineRepository(medicinePath);
    private final MedicineRequestRepository medicineRequestRepository = new MedicineRequestRepository(medicineRequestPath);
    private final PrescriptionRepository prescriptionRepository = new PrescriptionRepository(prescriptionPath);
    private final AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository = new AppointmentOutcomeRecordRepository(appointmentOutcomeRecordPath, prescriptionRepository);
    private final PatientRepository patientRepository = new PatientRepository(patientPath, appointmentRepository, appointmentOutcomeRecordRepository);
    private final StaffRepository staffRepository = new StaffRepository(staffPath);
    private final DoctorRepository doctorRepository = new DoctorRepository(doctorDir, staffRepository);

    public Menu createMenu(String role) {
        switch (role) {
            case "PATIENT":
                return new PatientMenu(patientRepository,
                        doctorRepository,
                        appointmentRepository,
                        appointmentOutcomeRecordRepository,
                        scanner);
            case "DOCTOR":
                return new DoctorMenu(scanner,
                        patientRepository,
                        staffRepository,
                        doctorRepository,
                        appointmentRepository,
                        prescriptionRepository,
                        appointmentOutcomeRecordRepository);
            case "PHARMACIST":
                return new PharmacistMenu(scanner,
                        staffRepository,
                        medicineRepository,
                        appointmentOutcomeRecordRepository,
                        medicineRequestRepository,
                        prescriptionRepository);
            case "ADMINISTRATOR":
                return new AdministratorMenu(staffRepository,
                        appointmentRepository,
                        appointmentOutcomeRecordRepository,
                        prescriptionRepository,
                        medicineRepository,
                        medicineRequestRepository,
                        scanner);
        }
        return null;
    }

}
