package com.resolver.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolver.dto.TicketRequest;
import com.resolver.entity.Ticket;
import com.resolver.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    
    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

@PostMapping
public Ticket createTicket(@Valid @RequestBody TicketRequest request){
    Ticket ticket = new Ticket();
    ticket.setTitle(request.getTitle());
    ticket.setDescription(request.getDescription());
    ticket.setStatus(request.getStatus());

    return ticketService.createTicket(ticket);
}

@GetMapping
public List<Ticket> getAllTickets() throws InterruptedException
{
    Thread.sleep(3000);
    System.out.println("Resolver called");
    return ticketService.getAllTickets();
}
@GetMapping("/{id}")
public Ticket getTicket(@PathVariable Long id) throws InterruptedException {
    return ticketService.getTicketById(id);
}

}
