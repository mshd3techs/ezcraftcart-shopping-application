package com.ezcraftcart.identity.web;

import com.ezcraftcart.identity.domain.User;
import com.ezcraftcart.identity.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login and registration")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        User user = authService.getUserByEmail(request.getEmail());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .build());
    }

    @PostMapping("/register")
    @Operation(summary = "Register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        String token = authService.register(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName());
        User user = authService.getUserByEmail(request.getEmail());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .build());
    }
}
