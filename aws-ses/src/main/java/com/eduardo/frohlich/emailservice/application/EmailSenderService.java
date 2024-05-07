package com.eduardo.frohlich.emailservice.application;

import com.eduardo.frohlich.emailservice.adapters.EmailSenderGateway;
import com.eduardo.frohlich.emailservice.core.EmailSenderUseCase;
import com.eduardo.frohlich.emailservice.core.HtmlEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class EmailSenderService implements EmailSenderUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    private final EmailSenderGateway emailSenderGateway;

    @Autowired
    public EmailSenderService(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        emailSenderGateway.sendEmail(to, subject, body);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String body) {
        emailSenderGateway.sendHtmlEmail(to, subject, body);
    }

    @Async
    public void htmlSend(HtmlEmailRequest HTMLrequest, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);

        String templateName = HTMLrequest.templateName();
        String to = HTMLrequest.to();
        String subject = HTMLrequest.subject();

        String processedHtml = templateEngine.process(templateName, context);
        LOGGER.debug("Processed HTML: {}", processedHtml);
        sendHtmlEmail(to, subject, processedHtml);
    }

}
