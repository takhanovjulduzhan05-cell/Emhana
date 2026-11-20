package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.*;
import java.util.*;
/** Booking use cases; only domain contracts, never infrastructure classes. */
public final class AppointmentService {
    private final ClinicRepository repository;
    private final BookingValidator validation;
    private final List<BookingObserver> observers = new ArrayList<>();
    private int counter=1;
    public AppointmentService(ClinicRepository repository, BookingValidator validation){this.repository=repository;this.validation=validation;}
    public void subscribe(BookingObserver observer){observers.add(observer);}
    private void notifyAll(BookingEvent e){observers.forEach(o->o.update(e));}
    public List<Doctor> doctors(){return repository.doctors();}
    public List<TimeSlot> slots(){return repository.slots();}
    public List<Appointment> appointments(){return repository.appointments();}
    public Appointment book(String patient,String slotId){
        validation.validate(new BookingRequest(patient,slotId));
        TimeSlot s=repository.slot(slotId).orElseThrow();
        s.reserve();
        Appointment a=Appointment.builder().id("A-"+String.format("%03d",counter++))
            .patient(patient).doctor(s.doctorId()).slot(s.id()).build();
        repository.saveAppointment(a);
        notifyAll(new BookingEvent("BOOKED",a.id(),a.patientName()));
        return a;
    }
    public void cancel(String id){
        Appointment a=find(id);
        TimeSlot s=repository.slot(a.slotId()).orElseThrow();
        a.cancel();s.release();
        notifyAll(new BookingEvent("CANCELLED",a.id(),a.patientName()));
    }
    public void restore(String id){
        Appointment a=find(id);
        TimeSlot s=repository.slot(a.slotId()).orElseThrow();
        s.reserve();a.restore();
        notifyAll(new BookingEvent("RESTORED",a.id(),a.patientName()));
    }
    public Appointment find(String id){return repository.appointment(id).orElseThrow(()->new IllegalArgumentException("Unknown appointment"));}
}
