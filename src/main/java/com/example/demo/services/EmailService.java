package com.example.demo.services;

import javax.mail.MessagingException;

public interface EmailService {

    /**
     * Send Mail.
     *
     * @param to      the to address
     * @param subject the subject
     * @param text    the text
     */
    void sendMail(String to, String subject, String text);
    /**
     * The send welcome mail.
     *
     * @param salesPerson salesPerson
     * @param email  email
     * @throws MessagingException MessagingException
     */
    void sendWelcomeMail(String salesPerson, String email)throws MessagingException;

    /**
     * The send temp password.
     * @param password password
     * @param salesPerson salesPerson
     * @param email  email
     * @throws MessagingException MessagingException
     */
    void sendTempPassword(String password, String salesPerson, String email)throws MessagingException;
}

