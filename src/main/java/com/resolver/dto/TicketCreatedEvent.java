package com.resolver.dto;

public class TicketCreatedEvent {

    private Long ticketId;   // 👈 IMPORTANT: use Long (not long)
    private String title;
    private String status;

    // ✅ GETTERS & SETTERS

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}