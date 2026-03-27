package com.resolver.service;

import java.util.List;

import com.resolver.entity.Ticket;
import com.resolver.repository.TicketRepository;

public class TicketService {
    
private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


}
