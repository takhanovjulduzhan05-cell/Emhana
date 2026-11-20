package kz.kaznaru.capstone.service;

/** Observer + Adapter integration: messages are sent on booking events. */
public final class ReminderObserver implements BookingObserver {
    private final ReminderPort reminder;
    public ReminderObserver(ReminderPort reminder){this.reminder=reminder;}
    public void update(BookingEvent e){reminder.remind(e.patient(),e.kind()+" appointment "+e.appointmentId());}
}
