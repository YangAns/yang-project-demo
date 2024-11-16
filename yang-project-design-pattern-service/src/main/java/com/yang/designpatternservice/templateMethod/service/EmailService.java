package com.yang.designpatternservice.templateMethod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final NotificationEmailSender notificationEmailSender;
    private final PromotionalEmailSender promotionalEmailSender;

    @Autowired
    public EmailService(NotificationEmailSender notificationEmailSender, PromotionalEmailSender promotionalEmailSender) {
        this.notificationEmailSender = notificationEmailSender;
        this.promotionalEmailSender = promotionalEmailSender;

    }

    public void sendNotificationEmail(String recipient) {
        notificationEmailSender.sendEmail(recipient);
    }

    public void sendPromotionalEmail(String recipient) {
        promotionalEmailSender.sendEmail(recipient);
    }
}
