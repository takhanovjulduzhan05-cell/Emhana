package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.List;
public interface AppointmentAccess { List<Appointment> list(String role); void cancel(String role,String appointmentId); }
