package kz.kaznaru.capstone.domain;

import java.util.List;
import java.util.Optional;
/** Repository port: infrastructure is replaceable without changing services. */
public interface ClinicRepository {
    void saveDoctor(Doctor doctor);
    void saveSlot(TimeSlot slot);
    void saveAppointment(Appointment appointment);
    Optional<Doctor> doctor(String id);
    Optional<TimeSlot> slot(String id);
    Optional<Appointment> appointment(String id);
    List<Doctor> doctors();
    List<TimeSlot> slots();
    List<Appointment> appointments();
}
