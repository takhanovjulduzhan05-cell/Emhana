package kz.kaznaru.capstone;

import kz.kaznaru.capstone.domain.*;
import kz.kaznaru.capstone.infra.*;
import kz.kaznaru.capstone.service.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ClinicPatternsTest {
    private static class Fixture {
        final ClinicRepository repository = new InMemoryClinicRepository();
        final PatientValidator rule = new PatientValidator();
        final AppointmentService service;
        final LegacySmsGateway gateway = new LegacySmsGateway();
        Fixture() {
            repository.saveDoctor(new Doctor("D1","Aigul","Therapist"));
            repository.saveSlot(new TimeSlot("S1","D1",LocalDateTime.of(2026,11,23,9,0)));
            repository.saveSlot(new TimeSlot("S2","D1",LocalDateTime.of(2026,11,23,10,0)));
            rule.then(new SlotValidator(repository));
            service = new AppointmentService(repository,rule);
            service.subscribe(new ReminderObserver(new SmsReminderAdapter(gateway)));
        }
    }
    @Test void stateRejectsDoubleBooking(){
        Fixture f=new Fixture();f.service.book("Aruzhan","S1");
        assertEquals("RESERVED",f.repository.slot("S1").orElseThrow().status());
        assertThrows(IllegalStateException.class,()->f.service.book("Dias","S1"));
    }
    @Test void commandUndoRestoresAppointmentAndSlot(){
        Fixture f=new Fixture();Appointment a=f.service.book("Aruzhan","S1");
        CommandHistory h=new CommandHistory();h.execute(new CancelAppointmentCommand(f.service,a.id()));
        assertTrue(a.cancelled());assertEquals("AVAILABLE",f.repository.slot("S1").orElseThrow().status());
        h.undo();assertFalse(a.cancelled());assertEquals("RESERVED",f.repository.slot("S1").orElseThrow().status());
        assertThrows(IllegalStateException.class,h::undo);
    }
    @Test void observerAndAdapterDispatchNotifications(){
        Fixture f=new Fixture();Appointment a=f.service.book("Aruzhan","S1");f.service.cancel(a.id());
        assertEquals(2,f.gateway.sent().size());assertTrue(f.gateway.sent().get(0).contains("BOOKED"));
    }
    @Test void builderRequiresFields(){
        assertThrows(NullPointerException.class,()->Appointment.builder().id("A1").patient("A").build());
        assertThrows(IllegalArgumentException.class,()->Appointment.builder().id("A1").patient(" ").doctor("D1").slot("S1").build());
    }
    @Test void factoryMethodHasIndependentTicketVariants(){
        assertEquals("R-001",new RegularTicketFactory().issue(1).code());
        assertEquals("P-002",new PriorityTicketFactory().issue(2).code());
        assertThrows(IllegalArgumentException.class,()->new RegularTicketFactory().issue(0));
    }
    @Test void strategyIsReplaceableWithoutChangingService(){
        Fixture f=new Fixture();f.service.book("Zarina","S1");f.service.book("Aruzhan","S2");
        QueueFacade byName=new QueueFacade(f.service,new ByPatientQueueStrategy(),new RegularTicketFactory());
        QueueFacade bySlot=new QueueFacade(f.service,new BySlotQueueStrategy(),new RegularTicketFactory());
        assertTrue(byName.screen().get(0).contains("Aruzhan"));
        assertTrue(bySlot.screen().get(0).contains("Zarina"));
    }
    @Test void proxyRestrictsAccess(){
        Fixture f=new Fixture();Appointment a=f.service.book("Aruzhan","S1");
        RoleAccessProxy proxy=new RoleAccessProxy(new RealAppointmentAccess(f.service));
        assertThrows(SecurityException.class,()->proxy.list("PATIENT"));
        assertEquals(1,proxy.list("RECEPTION").size());
        assertThrows(SecurityException.class,()->proxy.cancel("RECEPTION",a.id()));
        proxy.cancel("ADMIN",a.id());assertTrue(a.cancelled());
    }
    @Test void chainRejectsInvalidRequest(){
        Fixture f=new Fixture();assertThrows(IllegalArgumentException.class,()->f.service.book(" ","S1"));
        assertThrows(IllegalArgumentException.class,()->f.service.book("Aruzhan","invalid"));
    }
    @Test void iteratorGivesStableSnapshot(){
        Fixture f=new Fixture();Appointment a=f.service.book("Aruzhan","S1");
        List<Appointment> changed=new ArrayList<>(f.service.appointments());
        QueueSnapshot snapshot=new QueueSnapshot(changed);changed.clear();
        assertEquals(a.id(),snapshot.iterator().next().id());
    }
    @Test void facadeHidesQueueConstructionAndCancellationExcludesEntry(){
        Fixture f=new Fixture();Appointment a=f.service.book("Aruzhan","S1");
        QueueFacade view=new QueueFacade(f.service,new BySlotQueueStrategy(),new RegularTicketFactory());
        assertEquals(1,view.screen().size());f.service.cancel(a.id());assertTrue(view.screen().isEmpty());
    }
    @Test void commandCannotUndoTwice(){
        Fixture f=new Fixture();Appointment a=f.service.book("Aruzhan","S1");
        CancelAppointmentCommand c=new CancelAppointmentCommand(f.service,a.id());
        assertThrows(IllegalStateException.class,c::undo);
        c.execute();assertThrows(IllegalStateException.class,c::execute);c.undo();
    }
}
