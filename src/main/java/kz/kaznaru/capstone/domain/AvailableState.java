package kz.kaznaru.capstone.domain;

public final class AvailableState implements SlotState {
    public String label() { return "AVAILABLE"; }
    public SlotState reserve() { return new ReservedState(); }
    public SlotState release() { throw new IllegalStateException("Slot is already free"); }
}
