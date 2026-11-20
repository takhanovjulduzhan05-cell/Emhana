package kz.kaznaru.capstone.service;

public final class PriorityTicketFactory extends TicketFactory {
    protected QueueTicket create(int number){return new QueueTicket("P-"+String.format("%03d",number),"PRIORITY");}
}
