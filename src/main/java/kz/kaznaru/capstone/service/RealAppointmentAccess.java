package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.List;
public final class RealAppointmentAccess implements AppointmentAccess {
    private final AppointmentService service;
    public RealAppointmentAccess(AppointmentService service){this.service=service;}
    public List<Appointment> list(String role){return service.appointments();}
    public void cancel(String role,String id){service.cancel(id);}
}
