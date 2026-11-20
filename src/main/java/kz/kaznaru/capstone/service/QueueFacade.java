package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.*;
import java.util.*;
/** Facade: a single API for the reception queue screen. */
public final class QueueFacade {
    private final AppointmentService bookings;
    private final QueueStrategy policy;
    private final TicketFactory ticketFactory;
    public QueueFacade(AppointmentService bookings,QueueStrategy policy,TicketFactory factory){
        this.bookings=bookings;this.policy=policy;this.ticketFactory=factory;
    }
    public List<String> screen(){
        List<Appointment> active=bookings.appointments().stream().filter(a->!a.cancelled()).toList();
        List<Appointment> sorted=policy.order(active);
        List<String> result=new ArrayList<>();
        int number=1;
        for(Appointment a:new QueueSnapshot(sorted)){
            QueueTicket ticket=ticketFactory.issue(number++);
            result.add(ticket.code()+" | "+a.patientName()+" | "+a.slotId());
        }
        return List.copyOf(result);
    }
}
