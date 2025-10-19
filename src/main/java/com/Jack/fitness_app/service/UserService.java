package com.Jack.fitness_app.service;

import com.Jack.fitness_app.dto.UserProfileDto;
import com.Jack.fitness_app.model.User;
import com.Jack.fitness_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserProfileDto getProfile(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDto profile = new UserProfileDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getHeight(),
                user.getWeight(),
                user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : null
        );
        return profile;
    }

    public void updateProfile(String email,UserProfileDto userProfileDto){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userProfileDto.firstName() != null) user.setFirstName(userProfileDto.firstName());
        if (userProfileDto.lastName() != null) user.setLastName(userProfileDto.lastName());
        if (userProfileDto.phoneNumber() != null) user.setPhoneNumber(userProfileDto.phoneNumber());
        if (userProfileDto.height() != null) user.setHeight(userProfileDto.height());
        if (userProfileDto.weight() != null) user.setWeight(userProfileDto.weight());

        // Handle dateOfBirth safely
        if (userProfileDto.dateOfBirth() != null && !userProfileDto.dateOfBirth().isBlank()) {
            try {
                LocalDate dob = LocalDate.parse(userProfileDto.dateOfBirth());
                user.setDateOfBirth(dob);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
            }
        }

        userRepository.save(user);

    }

}
