package com.yang.designpatternservice.templateMethod.service;

import org.springframework.stereotype.Component;

@Component
public class NotificationEmailSender extends EmailSenderTemplate {

    @Override
    protected void prepareEmail() {
        System.out.println("Preparing notification email content.");
    }

    @Override
    protected void postSend() {
        System.out.println("Logging notification email send operation.");
    }
}