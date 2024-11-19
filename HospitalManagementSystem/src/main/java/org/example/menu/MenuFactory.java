package org.example.menu;

import org.example.repository.*;

import java.io.File;
import java.util.Scanner;

/**
 * A factory class to create different menus
 * It also handles dependencies injection
 */
public class MenuFactory {
    private final Scanner scanner = new Scanner(System.in);
    private final String writableBasePath = System.getProperty("user.dir") + "/data";

    private final String appointmentOutcomeRecordPath = "AppointmentOutcomeRecord.csv";
    private final String appointmentPath = "Appointment_List.csv";
    private final String doctorDir = "doctor_availability/";
    private final String medicinePath = "Medicine_List.csv";
    private final String medicineRequestPath = "Medicine_Requests.csv";
    private final String patientPath = "Patient_List.csv";
    private final String prescriptionPath = "Prescription.csv";
    private final String staffPath = "Staff_List.csv";

    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineRequestRepository medicineRequestRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentOutcomeRecordRepository appointmentOutcomeRecordRepository;
    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;
    private final DoctorRepository doctorRepository;

    public MenuFactory() {
        checkWritableBasePathExists();
        appointmentRepository = new AppointmentRepository(getWritableBasePath(appointmentPath));
        medicineRepository = new MedicineRepository(getWritableBasePath(medicinePath));
        medicineRequestRepository = new MedicineRequestRepository(getWritableBasePath(medicineRequestPath));
        prescriptionRepository = new PrescriptionRepository(getWritableBasePath(prescriptionPath));
        appointmentOutcomeRecordRepository = new AppointmentOutcomeRecordRepository(getWritableBasePath(appointmentOutcomeRecordPath), prescriptionRepository);
        patientRepository = new PatientRepository(getWritableBasePath(patientPath), appointmentRepository, appointmentOutcomeRecordRepository);
        staffRepository = new StaffRepository(getWritableBasePath(staffPath));
        doctorRepository = new DoctorRepository(getWritableBasePath(doctorDir), staffRepository);
    }

    private String getWritableBasePath(String fileName) {
        return writableBasePath + "/" + fileName;
    }

    public void checkWritableBasePathExists() {
        File dir = new File(writableBasePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dir = new File(writableBasePath + "/" + doctorDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    /**
     * Creates a menu based on the role of the user
     *
     * @param role the role of the user
     * @return the menu object
     */
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
