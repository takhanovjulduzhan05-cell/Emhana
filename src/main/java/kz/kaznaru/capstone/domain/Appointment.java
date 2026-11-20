package kz.kaznaru.capstone.domain;

import java.util.Objects;
/** Builder: a valid booking is created with all required fields. */
public final class Appointment {
    private final String id, patientName, doctorId, slotId;
    private boolean cancelled;
    private Appointment(Builder b) {
        id = Objects.requireNonNull(b.id); patientName=Objects.requireNonNull(b.patientName);
        doctorId=Objects.requireNonNull(b.doctorId); slotId=Objects.requireNonNull(b.slotId);
        if(patientName.isBlank()) throw new IllegalArgumentException("Patient name is blank");
    }
    public String id(){return id;}
    public String patientName(){return patientName;}
    public String doctorId(){return doctorId;}
    public String slotId(){return slotId;}
    public boolean cancelled(){return cancelled;}
    public void cancel(){if(cancelled)throw new IllegalStateException("Already cancelled");cancelled=true;}
    public void restore(){if(!cancelled)throw new IllegalStateException("Not cancelled");cancelled=false;}
    public static Builder builder(){return new Builder();}
    public static final class Builder {
        private String id, patientName, doctorId, slotId;
        public Builder id(String v){id=v;return this;}
        public Builder patient(String v){patientName=v;return this;}
        public Builder doctor(String v){doctorId=v;return this;}
        public Builder slot(String v){slotId=v;return this;}
        public Appointment build(){return new Appointment(this);}
    }
}
