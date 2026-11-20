package kz.kaznaru.capstone.service;

/** Factory Method: concrete factories decide how tickets are constructed. */
public abstract class TicketFactory {
    public final QueueTicket issue(int number){if(number<1)throw new IllegalArgumentException();return create(number);}
    protected abstract QueueTicket create(int number);
}
