package com.yang.designpatternservice.templateMethod.service;

import org.springframework.stereotype.Component;

@Component
public class PromotionalEmailSender extends EmailSenderTemplate {

    @Override
    protected void prepareEmail() {
        System.out.println("Preparing promotional email content.");
    }

    @Override
    protected void postSend() {
        System.out.println("Updating promotional email metrics.");
    }
}
