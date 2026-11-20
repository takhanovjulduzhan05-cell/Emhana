package kz.kaznaru.capstone.domain;

public final class ReservedState implements SlotState {
    public String label() { return "RESERVED"; }
    public SlotState reserve() { throw new IllegalStateException("Slot already reserved"); }
    public SlotState release() { return new AvailableState(); }
}
