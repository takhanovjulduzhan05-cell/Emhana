package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.*;
public final class ByPatientQueueStrategy implements QueueStrategy {
    public List<Appointment> order(List<Appointment> items) {
        return items.stream().sorted(Comparator.comparing(Appointment::patientName)).toList();
    }
}
