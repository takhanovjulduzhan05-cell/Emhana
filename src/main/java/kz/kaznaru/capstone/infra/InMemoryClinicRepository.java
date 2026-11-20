package kz.kaznaru.capstone.infra;

import kz.kaznaru.capstone.domain.*;
import java.util.*;
public final class InMemoryClinicRepository implements ClinicRepository {
    private final Map<String,Doctor> doctors=new LinkedHashMap<>();
    private final Map<String,TimeSlot> slots=new LinkedHashMap<>();
    private final Map<String,Appointment> appointments=new LinkedHashMap<>();
    public void saveDoctor(Doctor d){doctors.put(d.id(),d);}
    public void saveSlot(TimeSlot s){slots.put(s.id(),s);}
    public void saveAppointment(Appointment a){appointments.put(a.id(),a);}
    public Optional<Doctor> doctor(String id){return Optional.ofNullable(doctors.get(id));}
    public Optional<TimeSlot> slot(String id){return Optional.ofNullable(slots.get(id));}
    public Optional<Appointment> appointment(String id){return Optional.ofNullable(appointments.get(id));}
    public List<Doctor> doctors(){return List.copyOf(doctors.values());}
    public List<TimeSlot> slots(){return List.copyOf(slots.values());}
    public List<Appointment> appointments(){return List.copyOf(appointments.values());}
}
