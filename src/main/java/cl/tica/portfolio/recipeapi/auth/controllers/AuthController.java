package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.request.LoginRequest;
import cl.tica.portfolio.recipeapi.auth.dto.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.dto.response.RegisteredUserResponse;
import cl.tica.portfolio.recipeapi.auth.dto.response.TokenResponse;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.auth.security.jwt.JwtUtils;
import cl.tica.portfolio.recipeapi.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final UserService service;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(UserService service, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        if (service.existsByUsername(request.username())) {
            throw new UserAlreadyExistException(HttpStatus.CONFLICT, "Username is already taken!");
        }

        if (service.existsByEmail(request.email())) {
            throw new UserAlreadyExistException(HttpStatus.CONFLICT, "Email already exists");
        }

        User userRecord = service.save(new User(request.username(), request.email(), request.password()));
        return ResponseEntity.ok(new RegisteredUserResponse(
                userRecord.getId(),
                userRecord.getUsername(),
                userRecord.getEmail(),
                userRecord.getRoles()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.generateToken(authentication);
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }
}
