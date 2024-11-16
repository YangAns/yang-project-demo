package com.yang.designpatternservice.templateMethod.service;

public abstract class EmailSenderTemplate {

    // 模板方法，定义邮件发送的通用流程
    public void sendEmail(String recipient) {
        prepareEmail();
        setRecipient(recipient);
        send();
        postSend();
    }

    // 抽象方法，子类提供具体实现
    protected abstract void prepareEmail();
    
    // 设置收件人，子类可以重写这个方法
    protected void setRecipient(String recipient) {
        System.out.println("Sending email to " + recipient);
    }

    // 发送邮件，子类可以重写这个方法
    protected void send() {
        System.out.println("Email has been sent.");
    }

    // 邮件发送后的一些后续操作，子类可以根据需要重写
    protected void postSend() {
        System.out.println("Post send operations.");
    }
}
