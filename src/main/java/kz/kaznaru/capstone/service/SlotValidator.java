package kz.kaznaru.capstone.service;

import kz.kaznaru.capstone.domain.*;
public final class SlotValidator extends BookingValidator {
    private final ClinicRepository repository;
    public SlotValidator(ClinicRepository repository){this.repository=repository;}
    protected void check(BookingRequest r){
        TimeSlot s=repository.slot(r.slotId()).orElseThrow(()->new IllegalArgumentException("Unknown slot"));
        if(repository.doctor(s.doctorId()).isEmpty())throw new IllegalStateException("Unknown doctor");
        if(!s.status().equals("AVAILABLE"))throw new IllegalStateException("Slot already reserved");
    }
}
