package com.eduardo.frohlich.emailservice.controllers;

import com.eduardo.frohlich.emailservice.application.EmailSenderService;
import com.eduardo.frohlich.emailservice.core.EmailRequest;
import com.eduardo.frohlich.emailservice.core.HtmlEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailSenderService.sendEmail(emailRequest.to(), emailRequest.subject(), emailRequest.body());
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }

    @PostMapping("/sendHtmlEmail")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody HtmlEmailRequest htmlEmailRequest) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("name", htmlEmailRequest.name());
            model.put("password", "123456");

            emailSenderService.htmlSend(htmlEmailRequest, model);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email: " + e.getMessage());
        }
    }
}

