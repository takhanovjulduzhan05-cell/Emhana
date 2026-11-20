package kz.kaznaru.capstone.domain;

/** State pattern: slot behavior depends on its current state. */
public interface SlotState {
    String label();
    SlotState reserve();
    SlotState release();
}
