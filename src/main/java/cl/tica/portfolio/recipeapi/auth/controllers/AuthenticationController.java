package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.application.models.ExceptionWrappingError;
import cl.tica.portfolio.recipeapi.auth.dto.request.LoginRequest;
import cl.tica.portfolio.recipeapi.auth.dto.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.dto.response.RegisteredUserResponse;
import cl.tica.portfolio.recipeapi.auth.dto.response.TokenResponse;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.security.jwt.JwtUtils;
import cl.tica.portfolio.recipeapi.auth.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Auth Controller")
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationService service, AuthenticationManager authenticationManager,
                                    JwtUtils jwtUtils) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Operation(summary = "Register a new user.")
    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema =
    @Schema(implementation = RegisteredUserResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema =
    @Schema(implementation = ExceptionWrappingError.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema()))
    @PostMapping("/register")
    public ResponseEntity<RegisteredUserResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        User user = service.register(new User(request.username(), request.email(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisteredUserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        ));
    }

    @Operation(summary = "Get token for registered user.")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema =
    @Schema(implementation = TokenResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json", schema =
    @Schema(implementation = InvalidCredentialsException.class)))
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
            throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED,
                    "Credentials do not match or the user is not activated.");
        }
    }
}
