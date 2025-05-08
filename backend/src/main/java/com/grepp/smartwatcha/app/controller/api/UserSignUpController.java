package com.grepp.smartwatcha.app.controller.api;

import com.grepp.smartwatcha.app.model.SignUpRequest;
import com.grepp.smartwatcha.app.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class UserSignUpController {
    private final UserSignUpService userSignUpService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            Long userId = userSignUpService.signUp(request);
            return ResponseEntity.ok(userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 