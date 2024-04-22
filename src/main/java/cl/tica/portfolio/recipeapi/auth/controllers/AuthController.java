package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.dto.response.RegisteredUserResponse;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistsException;
import cl.tica.portfolio.recipeapi.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        if (service.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT, "Username is already taken!");
        }

        if (service.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT, "Email already exists");
        }

        User userRecord = service.save(new User(request.username(), request.email(), request.password()));
        return ResponseEntity.ok(new RegisteredUserResponse(
                userRecord.getId(),
                userRecord.getUsername(),
                userRecord.getEmail(),
                userRecord.getRoles()
        ));
    }
}
