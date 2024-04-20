package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.payload.request.LoginRequest;
import cl.tica.portfolio.recipeapi.auth.dto.payload.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.dto.payload.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(new UserResponse());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(new UserResponse());
    }

    @PostMapping("/logout")
    public ResponseEntity<UserResponse> logoutUser() {
        return ResponseEntity.ok(new UserResponse());
    }
}
