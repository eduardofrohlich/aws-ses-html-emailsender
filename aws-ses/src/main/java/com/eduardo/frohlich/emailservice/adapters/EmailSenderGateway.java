package com.eduardo.frohlich.emailservice.adapters;

public interface EmailSenderGateway {
    void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String body);
}
