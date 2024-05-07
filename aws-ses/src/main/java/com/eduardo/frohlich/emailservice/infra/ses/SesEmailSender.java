package com.eduardo.frohlich.emailservice.infra.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.eduardo.frohlich.emailservice.adapters.EmailSenderGateway;
import com.eduardo.frohlich.emailservice.core.exceptions.EmailServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesEmailSender implements EmailSenderGateway {
    private static final Logger LOGGER = LoggerFactory.getLogger(SesEmailSender.class);

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("<YOUR-AWS-MAIL>.com")
                .withDestination(new Destination().withToAddresses(toEmail))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body().withText(new Content(body)))
                );

        try {
            amazonSimpleEmailService.sendEmail(request);
        } catch (AmazonServiceException ex) {
            throw new EmailServiceException("Email sending failed", ex);
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("<YOUR-AWS-MAIL>.com")
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body().withHtml(new Content(body)))
                );
        try {
            amazonSimpleEmailService.sendEmail(request);
            LOGGER.info("Email sent successfully to {}", to);
        } catch (AmazonServiceException ex) {
            LOGGER.error("Error sending HTML email", ex);
            throw new EmailServiceException("Email sending failed", ex);
        }
    }
}