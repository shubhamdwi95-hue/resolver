package com.resolver.service;

import com.resolver.dto.TicketCreatedEvent;
import com.resolver.entity.Ticket;
import com.resolver.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    private final RedisService redisService;
    private final KafkaEventPublisher kafkaEventPublisher;

    // ✅ Constructor Injection (clean)
    public TicketService(TicketRepository ticketRepository,
                         RedisService redisService,
                         KafkaEventPublisher kafkaEventPublisher) {
        this.ticketRepository = ticketRepository;
        this.redisService = redisService;
        this.kafkaEventPublisher = kafkaEventPublisher;
    }

    // ✅ CREATE
    public Ticket createTicket(Ticket ticket) {

        log.info("Creating new ticket with title: {}", ticket.getTitle());

        Ticket savedTicket = ticketRepository.save(ticket);

        log.info("Ticket created with ID: {}", savedTicket.getId());

        // 🔥 Kafka event
        TicketCreatedEvent event = new TicketCreatedEvent();
        event.setTicketId(savedTicket.getId());
        event.setTitle(savedTicket.getTitle());
        event.setStatus(savedTicket.getStatus());

        kafkaEventPublisher.publishTicketCreated(event);

        return savedTicket;
    }

    // ✅ GET ALL (no cache here)
    public List<Ticket> getAllTickets() {
        log.info("Fetching all tickets");
        return ticketRepository.findAll();
    }

    // 🔥 REDIS CACHE IMPLEMENTATION
    public Ticket getTicketById(Long id) {

        String key = "ticket:" + id;

        // 1️⃣ Check Redis
        Ticket cached = (Ticket) redisService.get(key);
        if (cached != null) {
            log.info("🔥 Ticket fetched from Redis cache");
            return cached;
        }

        // 2️⃣ DB call
        log.info("Fetching ticket from DB");
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // 3️⃣ Save to Redis
        redisService.save(key, ticket);

        return ticket;
    }

    // 🔥 UPDATE + CACHE EVICTION
    public Ticket updateTicket(Long id, Ticket updated) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setTitle(updated.getTitle());
        ticket.setDescription(updated.getDescription());
        ticket.setStatus(updated.getStatus());

        Ticket saved = ticketRepository.save(ticket);

        // ❗ Evict cache
        redisService.delete("ticket:" + id);

        log.info("Cache evicted for ticket {}", id);

        return saved;
    }
}