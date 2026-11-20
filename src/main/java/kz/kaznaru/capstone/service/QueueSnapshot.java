package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.*;
/** Iterator: callers traverse a stable, ordered snapshot rather than live storage. */
public final class QueueSnapshot implements Iterable<Appointment> {
    private final List<Appointment> snapshot;
    public QueueSnapshot(List<Appointment> items){snapshot=List.copyOf(items);}
    @Override public Iterator<Appointment> iterator(){return snapshot.iterator();}
}
