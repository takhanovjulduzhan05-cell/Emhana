package kz.kaznaru.capstone.service;

public final class RegularTicketFactory extends TicketFactory {
    protected QueueTicket create(int number){return new QueueTicket("R-"+String.format("%03d",number),"REGULAR");}
}
