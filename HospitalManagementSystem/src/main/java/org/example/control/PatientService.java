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

    public void updatePatientName(int patientId, String name) {
        patientRepository.updatePatientField(patientId, "name", name);
    }

    public void updatePatientBirthday(int patientId, LocalDate birthday) {

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
