package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.Appointment;
import java.util.List;
/** Strategy: configurable ordering rules for the queue screen. */
public interface QueueStrategy { List<Appointment> order(List<Appointment> appointments); }
