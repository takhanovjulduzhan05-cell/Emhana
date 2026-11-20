package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.*;
/** New queue policy is added as a new class; existing logic is untouched. */
public final class BySlotQueueStrategy implements QueueStrategy {
    public List<Appointment> order(List<Appointment> items){
        return items.stream().sorted(Comparator.comparing(Appointment::slotId)).toList();
    }
}
