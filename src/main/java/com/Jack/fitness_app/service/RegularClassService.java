package com.Jack.fitness_app.service;

import com.Jack.fitness_app.model.RegularClass;
import com.Jack.fitness_app.model.User;
import com.Jack.fitness_app.repository.RegularClassRepository;
import com.Jack.fitness_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegularClassService {
    private final RegularClassRepository regularClassRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public RegularClassService(RegularClassRepository regularClassRepository) {
        this.regularClassRepository = regularClassRepository;
    }

    public void addRegularClass(RegularClass regularClass) {
        regularClassRepository.save(regularClass);
    }

    public List<RegularClass> getAllRegularClass() {
        return regularClassRepository.findAll();
    }

    public RegularClass viewRegularClass(String id) {
        return regularClassRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Regular class not found with id: " + id)
        );
    }

    public void enrolRegularClass(String id, String email) {
        RegularClass regularClass = regularClassRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Regular class not found with id: " + id)
        );

        // Check if class is full
        if (regularClass.getCurrentNumber() >= regularClass.getMaxNumber()) {
            throw new RuntimeException("Class is already full");
        }

        // Increment current enrollment count
        regularClass.setCurrentNumber(regularClass.getCurrentNumber() + 1);
        regularClassRepository.save(regularClass);

        // Send confirmation email
        sendEnrolEmail(id, email);
    }

    private void sendEnrolEmail(String id, String email) {
        RegularClass regularClass = regularClassRepository.findById(id).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Regular Class Enrollment Confirmation - " + regularClass.getName());
        message.setText(
                "Dear " + user.getFirstName() + ",\n\n" +
                        "You have successfully enrolled in the following regular class:\n\n" +
                        "Class: " + regularClass.getName() + "\n" +
                        "Description: " + regularClass.getDescription() + "\n" +
                        "Schedule: " + regularClass.getDateAndTime() + "\n" +
                        "Price: £" + regularClass.getPrice() + "\n\n" +
                        "We look forward to seeing you!\n\n" +
                        "Best regards,\n" +
                        "FitWithJack Team"
        );

        mailSender.send(message);
    }
}
