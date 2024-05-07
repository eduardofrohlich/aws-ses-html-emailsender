package com.eduardo.frohlich.emailservice.core;

public record EmailRequest(String to, String subject, String body) {
}
