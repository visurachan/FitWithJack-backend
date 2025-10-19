package com.Jack.fitness_app.controller;

import com.Jack.fitness_app.model.RegularClass;
import com.Jack.fitness_app.service.RegularClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regularClass")
public class RegularClassController {
    private final RegularClassService regularClassService;

    public RegularClassController(RegularClassService regularClassService) {
        this.regularClassService = regularClassService;
    }

    @PostMapping("/addRegularClass")
    public void addRegularClass(@RequestBody RegularClass regularClass){
        regularClassService.addRegularClass(regularClass);
    }

    @GetMapping("/viewRegularClassList")
    public List<RegularClass> getRegularClassList(){
        return regularClassService.getAllRegularClass();
    }

    @GetMapping("/viewRegularClass/{id}")
    public ResponseEntity<RegularClass> viewRegularClass(@PathVariable("id") String id){
        RegularClass regularClass = regularClassService.viewRegularClass(id);
        return ResponseEntity.ok(regularClass);
    }

    @PostMapping("/enrolRegularClass/{id}")
    public String enrolRegularClass(@PathVariable("id") String id, Authentication authentication){
        String email = authentication.getName();
        regularClassService.enrolRegularClass(id, email);
        return "Enrolment confirmation sent to email!";
    }
}
