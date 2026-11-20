package kz.kaznaru.capstone.ui;

import kz.kaznaru.capstone.domain.*;
import kz.kaznaru.capstone.service.*;
import java.util.*;
/** UI depends on services, not infrastructure. */
public final class ClinicConsole {
    private final AppointmentService service;
    private final QueueFacade queue;
    private final RoleAccessProxy access;
    private final CommandHistory history;
    public ClinicConsole(AppointmentService service, QueueFacade queue, RoleAccessProxy access, CommandHistory history){
        this.service=service;this.queue=queue;this.access=access;this.history=history;
    }
    public void demo(){
        System.out.println("====================================================");
        System.out.println("      EMHANA | CAPSTONE PROJECT | M1-M3 (v0.3)");
        System.out.println("      Authors: Alisher & Zhuldyzkhan");
        System.out.println("====================================================");
        System.out.println("DOCTORS:");
        for(Doctor d:service.doctors())System.out.println("  "+d.id()+" | "+d.name()+" | "+d.specialty());
        System.out.println("SLOTS:");
        for(TimeSlot s:service.slots())System.out.println("  "+s.id()+" | "+s.startsAt()+" | "+s.status());
        Appointment a=service.book("Aruzhan", "S1");
        Appointment b=service.book("Dias", "S2");
        System.out.println("[BOOKED] "+a.id()+" and "+b.id());
        printQueue();
        history.execute(new CancelAppointmentCommand(service,a.id()));
        System.out.println("[CANCELLED] "+a.id());printQueue();
        history.undo();System.out.println("[UNDO] Cancellation reversed");printQueue();
        try{service.book("Azamat","S1");}catch(IllegalStateException ex){System.out.println("[BLOCKED] "+ex.getMessage());}
        try{access.list("PATIENT");}catch(SecurityException ex){System.out.println("[PROXY] "+ex.getMessage());}
        System.out.println("[STAFF ACCESS] "+access.list("RECEPTION").size()+" bookings visible");
        System.out.println("[PASS] M3 demo completed");
    }
    private void printQueue(){System.out.println("QUEUE SCREEN:");queue.screen().forEach(s->System.out.println("  "+s));}
    public void menu(Scanner in){
        while(true){
            System.out.println("\n1 Doctors  2 Slots  3 Book  4 Cancel  5 Undo  6 Queue  0 Exit");
            String choice=in.nextLine().trim();
            try{switch(choice){
                case "1"->service.doctors().forEach(System.out::println);
                case "2"->service.slots().forEach(s->System.out.println(s.id()+" "+s.startsAt()+" "+s.status()));
                case "3"->{System.out.print("Patient: ");String patient=in.nextLine();System.out.print("Slot ID: ");String slot=in.nextLine();System.out.println("Booked "+service.book(patient,slot).id());}
                case "4"->{System.out.print("Appointment ID: ");history.execute(new CancelAppointmentCommand(service,in.nextLine()));System.out.println("Cancelled");}
                case "5"->{history.undo();System.out.println("Undo successful");}
                case "6"->printQueue();
                case "0"->{return;}
                default->System.out.println("Unknown option");
            }}catch(RuntimeException ex){System.out.println("[ERROR] "+ex.getMessage());}
        }
    }
}
