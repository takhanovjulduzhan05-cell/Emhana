package kz.kaznaru.capstone.service;

public final class PatientValidator extends BookingValidator {
    protected void check(BookingRequest r){if(r.patient()==null||r.patient().isBlank())throw new IllegalArgumentException("Patient name is blank");}
}
