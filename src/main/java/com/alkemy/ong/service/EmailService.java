package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SendGrid sendGrid;

    @Value("${sendgrid.organization.email}")
    private final String FROM;

    @Value("${sendgrid.organization.subject}")
    private final String SUBJECT;

    @Value("${sendgrid.organization.type}")
    private final String TYPE;

    @Value("${sendgrid.organization.body}")
    private final String BODY;

    public String sendEmail(String toEmail, String endPoint, Method method) {
        Mail mail = new Mail(new Email(FROM),
                SUBJECT,
                new Email(toEmail),
                new Content(TYPE, BODY));

        Request request = new Request();
        Response response = null;

        try {

            request.setMethod(method);
            request.setEndpoint(endPoint);
            request.setBody(mail.build());

            response = sendGrid.api(request);

        } catch (Exception e) {
            throw new RuntimeException("Error sending email via SendGrid to " + toEmail + ": " + response);
        }

        // TODO: <- return Response or String?
        return (response.getStatusCode() == 200 || response.getStatusCode() == 202) 
                ? "Email has been sent successfully, please check your inbox"
                : "Error sending email. Code: " + response.getStatusCode();
    }

}
