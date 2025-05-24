package com.krish.emailUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");
            helper.setFrom("your_email@gmail.com"); // must match your authenticated sender

            String content = "<p>Hello,</p>"
                    + "<p>You requested to reset your password.</p>"
                    + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>"
                    + "<p>If you did not request this, please ignore this email.</p>";

            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("Password reset email sent to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email to " + toEmail);
            e.printStackTrace(); // This should log the full stack trace
            throw new RuntimeException("Email sending failed", e);
        }
    }
    
    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("your_email@gmail.com");
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
            System.out.println("Email sent to " + to);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


}

