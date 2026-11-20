package kz.kaznaru.capstone.service;

/** Observer: listeners receive booking lifecycle notifications. */
public interface BookingObserver { void update(BookingEvent event); }
