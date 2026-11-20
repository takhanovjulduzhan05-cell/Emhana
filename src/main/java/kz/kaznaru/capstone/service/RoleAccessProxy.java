package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.List;
/** Proxy: only staff may inspect all bookings; only administrators cancel. */
public final class RoleAccessProxy implements AppointmentAccess {
    private final AppointmentAccess real;
    public RoleAccessProxy(AppointmentAccess real){this.real=real;}
    public List<Appointment> list(String role){
        if(!role.equals("ADMIN")&&!role.equals("RECEPTION"))throw new SecurityException("Access denied");
        return real.list(role);
    }
    public void cancel(String role,String id){
        if(!role.equals("ADMIN"))throw new SecurityException("Only ADMIN may cancel");
        real.cancel(role,id);
    }
}
