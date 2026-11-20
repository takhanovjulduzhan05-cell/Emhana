package kz.kaznaru.capstone;

import kz.kaznaru.capstone.domain.*;
import kz.kaznaru.capstone.infra.*;
import kz.kaznaru.capstone.service.*;
import kz.kaznaru.capstone.ui.ClinicConsole;
import java.time.LocalDateTime;
import java.util.Scanner;
/** Composition root: all concrete dependencies are wired here. */
public final class App {
    private App() { }
    public static void main(String[] args) {
        ClinicRepository repository=new InMemoryClinicRepository();
        repository.saveDoctor(new Doctor("D1","A. Saparov","Therapist"));
        repository.saveDoctor(new Doctor("D2","M. Omarova","Pediatrician"));
        repository.saveSlot(new TimeSlot("S1","D1",LocalDateTime.of(2026,11,23,9,0)));
        repository.saveSlot(new TimeSlot("S2","D2",LocalDateTime.of(2026,11,23,10,0)));
        repository.saveSlot(new TimeSlot("S3","D1",LocalDateTime.of(2026,11,23,11,0)));
        PatientValidator patientRules=new PatientValidator();
        patientRules.then(new SlotValidator(repository));
        AppointmentService service=new AppointmentService(repository,patientRules);
        LegacySmsGateway legacy=new LegacySmsGateway();
        service.subscribe(new ReminderObserver(new SmsReminderAdapter(legacy)));
        QueueFacade queue=new QueueFacade(service,new BySlotQueueStrategy(),new RegularTicketFactory());
        RoleAccessProxy proxy=new RoleAccessProxy(new RealAppointmentAccess(service));
        ClinicConsole console=new ClinicConsole(service,queue,proxy,new CommandHistory());
        if(args.length>0 && args[0].equals("--menu"))console.menu(new Scanner(System.in));
        else console.demo();
        System.out.println("REMINDERS SENT: "+legacy.sent().size());
    }
}
