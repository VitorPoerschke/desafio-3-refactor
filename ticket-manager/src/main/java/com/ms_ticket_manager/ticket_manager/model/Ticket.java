package com.ms_ticket_manager.ticket_manager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "tickets")
public class Ticket {
    @Id
    private String ticketId;
    private String cpf;
    private String customerName;
    private String customerMail;
    private String eventId;
    private String brlAmount;
    private String usdAmount;
    private String status;
    private Event event;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getBrlAmount() {
        return brlAmount;
    }

    public void setBrlAmount(String brlAmount) {
        this.brlAmount = brlAmount;
    }

    public String getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(String usdAmount) {
        this.usdAmount = usdAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", cpf='" + cpf + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerMail='" + customerMail + '\'' +
                ", eventId='" + eventId + '\'' +
                ", BRLtotalAmount='R$" + brlAmount + '\'' +
                ", USDtotalAmount='$" + usdAmount + '\'' +
                ", status='" + status + '\'' +
                ", event=" + event +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId) &&
                Objects.equals(cpf, ticket.cpf) &&
                Objects.equals(customerName, ticket.customerName) &&
                Objects.equals(customerMail, ticket.customerMail) &&
                Objects.equals(eventId, ticket.eventId) &&
                Objects.equals(brlAmount, ticket.brlAmount) &&
                Objects.equals(usdAmount, ticket.usdAmount) &&
                Objects.equals(status, ticket.status) &&
                Objects.equals(event, ticket.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, cpf, customerName, customerMail, eventId, brlAmount, usdAmount, status, event);
    }

}
