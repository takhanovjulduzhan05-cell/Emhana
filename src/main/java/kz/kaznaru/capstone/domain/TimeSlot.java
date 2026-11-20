package kz.kaznaru.capstone.domain;

import java.time.LocalDateTime;
public final class TimeSlot {
    private final String id;
    private final String doctorId;
    private final LocalDateTime startsAt;
    private SlotState state = new AvailableState();
    public TimeSlot(String id, String doctorId, LocalDateTime startsAt) {
        this.id=id; this.doctorId=doctorId; this.startsAt=startsAt;
    }
    public String id() { return id; }
    public String doctorId() { return doctorId; }
    public LocalDateTime startsAt() { return startsAt; }
    public String status() { return state.label(); }
    public void reserve() { state = state.reserve(); }
    public void release() { state = state.release(); }
}
