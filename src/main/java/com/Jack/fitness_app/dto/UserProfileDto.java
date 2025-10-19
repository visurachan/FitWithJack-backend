package com.Jack.fitness_app.dto;


import java.time.LocalDate;

public record UserProfileDto(
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    Float height,
    Float weight,
    String dateOfBirth
) { }


