package com.Jack.fitness_app.service;

import com.Jack.fitness_app.dto.OneTimeSessionDTO;
import com.Jack.fitness_app.model.OneTimeSession;
import com.Jack.fitness_app.repository.OneTimeSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OneTimeSessionService {

    @Autowired
    private JavaMailSender mailSender;

    private final OneTimeSessionRepository oneTimeSessionRepository;

    public OneTimeSessionService(OneTimeSessionRepository oneTimeSessionRepository) {
        this.oneTimeSessionRepository = oneTimeSessionRepository;
    }

    public  OneTimeSession viewOneTimeSession(String id) {
        return oneTimeSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

    }

    public void addOneTimeSession(OneTimeSession oneTimeSession) {
        oneTimeSessionRepository.save(oneTimeSession);
    }

    public List<OneTimeSession> getAllOneTimeSessions() {
        return oneTimeSessionRepository.findAll();
    }

    public void enrolOneTimeSession(String id, String email) {
        sendEnrolEmail(id,email);


    }

    private void sendEnrolEmail(String id, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        OneTimeSession oneTimeSession = viewOneTimeSession(id);
        message.setTo(email);
        message.setSubject("Enrolment receipt");
        message.setText("You are successfully enrolled in to "+oneTimeSession.getName()+" ON "+oneTimeSession.getDate()+" AT "+oneTimeSession.getTime());
        mailSender.send(message);

    }
}
