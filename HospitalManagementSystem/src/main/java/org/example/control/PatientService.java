package org.example.control;

import org.example.entity.Appointment;
import org.example.entity.AppointmentOutcomeRecord;
import org.example.entity.Patient;
import org.example.entity.PatientRepository;

import java.time.LocalDate;
import java.util.Date;

public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService() {
        patientRepository = new PatientRepository();
    }

    public Patient getPatientById(int id) {
        return patientRepository.getPatientById(id);
    }

    public void updateName(int patientId, String name) {
        patientRepository.updatePatientField(patientId, "name", name);
    }

    public void updateDateOfBirth(int patientId, int day, int month, int year) {
        LocalDate localDate = LocalDate.of(day, month, year);
        patientRepository.updatePatientField(patientId, "dateofbirth", localDate.format(PatientRepository.DATE_FORMATTER));
    }

    public void updateGender(int patientId, Patient.Gender gender) {
        patientRepository.updatePatientField(patientId, "gender", gender.name());
    }

    public void addAppointment(Appointment appointment) {

    }

    public void addOutcomeRecord(AppointmentOutcomeRecord outcomeRecord) {

    }

    // consider moving this to a control appointment manager - single responsibility principle
    public void scheduleAppointment(int doctorId, int timeslot) {

    }

    public void reScheduleAppointment(int doctorId, int timeslot) {

    }

    public void cancelAppointment(int doctorId, int timeslot) {

    }
}
