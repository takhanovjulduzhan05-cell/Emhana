package kz.kaznaru.capstone.service;

/** Chain of Responsibility: each handler checks one booking condition. */
public abstract class BookingValidator {
    private BookingValidator next;
    public BookingValidator then(BookingValidator next){this.next=next;return next;}
    public final void validate(BookingRequest request){check(request);if(next!=null)next.validate(request);}
    protected abstract void check(BookingRequest request);
}
