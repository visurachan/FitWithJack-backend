package com.Jack.fitness_app.repository;

import com.Jack.fitness_app.model.OneTimeSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeSessionRepository extends JpaRepository<OneTimeSession,String> {
}
