package com.resolver.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRabbitController {

    private final RabbitTemplate rabbitTemplate;

    public TestRabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/rabbit-test")
    public String sendMessage() {

        rabbitTemplate.convertAndSend("ticket_exchange", "ticket_routing", "test message");

        return "Message sent to RabbitMQ";
    }
}