package kz.kaznaru.capstone.service;

public record BookingEvent(String kind, String appointmentId, String patient) { }
