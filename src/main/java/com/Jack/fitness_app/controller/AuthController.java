package com.Jack.fitness_app.controller;


import com.Jack.fitness_app.dto.RequestCode;
import com.Jack.fitness_app.dto.RequestPassword;
import com.Jack.fitness_app.model.User;
import com.Jack.fitness_app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user){
        authService.registerUser(user);
        return "Verification code send to email!";
    }
    // Verify email
    @PostMapping("/verify")
    public String verify(@RequestParam String email, @RequestBody RequestCode requestCode) {
        boolean verified = authService.verifyCode(email, requestCode.getCode());
        return verified ? "Email verified! Now set your password." : "Invalid code!";
    }

    // Set password
    @PostMapping("/set-password")
    public String setPassword(@RequestParam String email, @RequestBody RequestPassword requestPassword) {
        boolean success = authService.setPassword(email, requestPassword.getPassword());
        return success ? "Password set successfully!" : "Email not verified!";
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestBody RequestPassword requestPassword) {
        String token = authService.login(email, requestPassword.getPassword());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token, "message", "Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials or email not verified"));
        }
    }







}
