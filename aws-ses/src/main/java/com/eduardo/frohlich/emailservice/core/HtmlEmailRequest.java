package com.eduardo.frohlich.emailservice.core;

public record HtmlEmailRequest(String subject, String to, String name, String templateName) {
    public HtmlEmailRequest {
        templateName = "account-password";
    }
}