package com.Jack.fitness_app.controller;

import com.Jack.fitness_app.dto.UserProfileDto;
import com.Jack.fitness_app.model.User;

import com.Jack.fitness_app.repository.UserRepository;
import com.Jack.fitness_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        UserProfileDto profile = userService.getProfile(authentication);
        return ResponseEntity.ok(profile);

    }
    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(Authentication authentication, @RequestBody UserProfileDto userProfileDto){
        String email = authentication.getName();
        userService.updateProfile(email,userProfileDto);
        return ResponseEntity.ok("Profile updated successfully!");

    }

}
