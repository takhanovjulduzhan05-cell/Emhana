package kz.kaznaru.capstone.service;

/** Undo cancellation restores the original appointment and reserves the slot. */
public final class CancelAppointmentCommand implements UndoableCommand {
    private final AppointmentService service;
    private final String id;
    private boolean executed;
    public CancelAppointmentCommand(AppointmentService service,String id){this.service=service;this.id=id;}
    public void execute(){if(executed)throw new IllegalStateException("Already executed");service.cancel(id);executed=true;}
    public void undo(){if(!executed)throw new IllegalStateException("Not executed");service.restore(id);executed=false;}
}
