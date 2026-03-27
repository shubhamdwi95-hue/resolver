package com.resolver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Service;

import com.resolver.entity.Ticket;
import com.resolver.repository.TicketRepository;

@Service
public class TicketService {

private static final Logger log = LoggerFactory.getLogger(TicketService.class);

private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    public Ticket createTicket(Ticket ticket) {

        log.info("Creating new ticket with title: {}", ticket.getTitle());

        Ticket savedTicket = ticketRepository.save(ticket);

        log.info("Ticket created with ID: {}", savedTicket.getId());

        return savedTicket;
    }

    public List<Ticket> getAllTickets() {

        log.info("Fetching all tickets");

        return ticketRepository.findAll();
    }
}
