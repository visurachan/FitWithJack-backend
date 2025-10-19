package com.Jack.fitness_app.controller;

import com.Jack.fitness_app.model.OneTimeSession;
import com.Jack.fitness_app.service.OneTimeSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oneTimeSession")
public class OneTimeSessionController {

    private final OneTimeSessionService oneTimeSessionService;

    public OneTimeSessionController(OneTimeSessionService oneTimeSessionService) {
        this.oneTimeSessionService = oneTimeSessionService;
    }

    @PostMapping("/addOneTimeSession")
    public void addOneTimeSession(@RequestBody OneTimeSession oneTimeSession) {
        oneTimeSessionService.addOneTimeSession(oneTimeSession);
    }

    @GetMapping("/viewOneTimeSessionList")
    public List<OneTimeSession> getOneTimeSessionList() {
        return oneTimeSessionService.getAllOneTimeSessions();
    }

    @GetMapping("/viewOneTimeSession/{id}")
    public ResponseEntity<OneTimeSession> viewOneTimeSession(@PathVariable ("id") String id){
        OneTimeSession session = oneTimeSessionService.viewOneTimeSession(id);
        return ResponseEntity.ok(session);

    }

    @PostMapping("/enrolSession/{id}")
    public String enrolOneTimeSession(@PathVariable ("id") String id, Authentication authentication){
        String email = authentication.getName();
        oneTimeSessionService.enrolOneTimeSession(id,email);
        return "Enrolment confirmation sent to email!";


    }
}

