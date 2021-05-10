package com.example.demo.services.impl;

import com.example.demo.exception.GenericException;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine template;

    @Value("${frontend.baseUrl}")
    private String baseUrl;

    /**
     * The email service implementation.
     *
     * @param mailSenderParam mailSenderParam
     * @param templateParam templateParam
     */
    @Autowired
    public EmailServiceImpl(final JavaMailSender mailSenderParam,
                            final TemplateEngine templateParam) {
        this.javaMailSender = mailSenderParam;
        this.template = templateParam;

    }

    /**
     * To send Mail.
     *
     * @param to      the to address
     * @param subject the subject
     * @param text    the text
     */
    @Override
    public void sendMail(final String to, final String subject,
                         final String text) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
//            message.setFrom(INFO_EMAIL_ID);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new GenericException(e.getLocalizedMessage());
        }
    }

    /**
     * To send Temporary Password.
     *
     * @param name         name
     * @param email        email
     */
    @Override
    public void sendWelcomeMail(final String name,
                                 final String email) {
        Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("frontendBaseUrl", baseUrl);
        ctx.setVariable("name", name);
//        ctx.setVariable("imageUrl", baseUrl + "assets/template-mockup.jpg");
        String text = template.process("TemporaryPasswordTemplate", ctx);
        String subject = "Welcome to JDBC";
        sendMail(email, subject, text);
    }

    @Override
    public void sendTempPassword(final String tempPassword, final String name, final String email) throws MessagingException {
        Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("frontendBaseUrl", baseUrl);
        ctx.setVariable("tempPassword", tempPassword);
        ctx.setVariable("name", name);
//        ctx.setVariable("imageUrl", baseUrl + "assets/template-mockup.jpg");
        String text = template.process("ForgetPasswordTemplate", ctx);
        String subject = "Your Temporary Password";
        sendMail(email, subject, text);

    }
}
