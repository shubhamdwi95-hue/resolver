package com.resolver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.resolver.dto.TicketCreatedEvent;

@Service
public class KafkaEventPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

   public void publishTicketCreated(TicketCreatedEvent event) {
        kafkaTemplate.send("ticket-created", event);
    }
}