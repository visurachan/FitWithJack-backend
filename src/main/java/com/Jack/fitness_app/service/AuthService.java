package com.Jack.fitness_app.service;

import com.Jack.fitness_app.model.User;
import com.Jack.fitness_app.repository.UserRepository;
import com.Jack.fitness_app.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;


import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User user) {
        user.setEmailVerified(false);
        user.setVerificationCode(generateVerificationCode());
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    private void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("verify your email");
        message.setText("Your verification code is: "+ user.getVerificationCode());
        mailSender.send(message);
    }

    private String generateVerificationCode(){
        return String.valueOf((int)(Math.random()*900000)+100000);
    }
    // Step 2: Verify code
    public boolean verifyCode(String email, String code) {
        Optional<User> optionalUser = userRepository.findByEmail(email.trim());


        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            // DEBUG: print the code from DB and the one sent
            System.out.println("DB code: '" + user.getVerificationCode() + "'");
            System.out.println("Received code: '" + code + "'");

            if(user.getVerificationCode() != null &&
                    user.getVerificationCode().trim().equals(code.trim())) {
                user.setEmailVerified(true);
                user.setVerificationCode(null); // clear code
                userRepository.save(user);
                System.out.println("Verification successful!");
                return true;
            } else {
                System.out.println("Verification failed: code mismatch.");
            }
        } else {
            System.out.println("Verification failed: email not found.");
        }
        return false;
    }


    public boolean setPassword(String email, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email.trim());
        if(optionalUser.isPresent() && optionalUser.get().isEmailVerified()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
            return true;
        }
        return false;

    }



    public String login(String email, String rawPassword) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword)
            );
            return jwtService.generateToken(email);
        } catch (AuthenticationException e) {
            return null; // invalid login
        }
    }


}
