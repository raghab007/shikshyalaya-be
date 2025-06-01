package com.e_learning.Sikshyalaya.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String hostEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEnrollmentConfirmation(String toEmail, String courseName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Course Enrollment Confirmation");
        message.setText("Dear Student,\n\nYou have successfully enrolled in the course: " + courseName +
                ".\n\nThank you for choosing our platform!");

        mailSender.send(message);
    }

    public void sendContactFormEmail(String name, String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(hostEmail); // Using the configured email address
        mailMessage.setSubject("New Contact Form Submission: " + subject);
        mailMessage.setText(
                "New contact form submission received:\n\n" +
                        "Name: " + name + "\n" +
                        "Email: " + email + "\n" +
                        "Subject: " + subject + "\n\n" +
                        "Message:\n" + message);

        // Send confirmation to the user
        SimpleMailMessage userConfirmation = new SimpleMailMessage();
        userConfirmation.setTo(email);
        userConfirmation.setSubject("Thank you for contacting Sikshyalaya");
        userConfirmation.setText(
                "Dear " + name + ",\n\n" +
                        "Thank you for contacting Sikshyalaya. We have received your message and will get back to you soon.\n\n"
                        +
                        "Best regards,\n" +
                        "Sikshyalaya Team");

        mailSender.send(mailMessage);
        mailSender.send(userConfirmation);
    }
}