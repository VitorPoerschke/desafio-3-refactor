package com.ms_ticket_manager.ticket_manager.dto;

public class TicketDTO {

    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String brlAmount;
    private String usdAmount;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    @Override
    public String toString() {
        return "TicketDTO{" +
                "customerName='" + customerName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", customerMail='" + customerMail + '\'' +
                ", eventId='" + eventId + '\'' +
                ", brlAmount='R$" + brlAmount + '\'' +
                ", usdAmount='$" + usdAmount + '\'' +
                '}';
    }
}
