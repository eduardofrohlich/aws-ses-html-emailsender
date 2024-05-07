package com.eduardo.frohlich.emailservice.core;

public interface EmailSenderUseCase {
    void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String body);
}
