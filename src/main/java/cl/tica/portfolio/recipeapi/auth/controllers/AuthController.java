package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.request.LoginRequest;
import cl.tica.portfolio.recipeapi.auth.dto.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.dto.response.RegisteredUserResponse;
import cl.tica.portfolio.recipeapi.auth.dto.response.TokenResponse;
import cl.tica.portfolio.recipeapi.auth.dto.response.UserConfirmationResponse;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidConfirmationException;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.security.jwt.JwtUtils;
import cl.tica.portfolio.recipeapi.auth.services.AuthService;
import cl.tica.portfolio.recipeapi.models.ExceptionWrappingError;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService service;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService service, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Operation(summary = "Register a new user.")
    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisteredUserResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrappingError.class)))
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
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidCredentialsException.class)))
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

    @Operation(summary = "Confirm user and email.")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserConfirmationResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrappingError.class)))
    @GetMapping("/confirm-account/{code}")
    public ResponseEntity<UserConfirmationResponse> confirmUserAccount(@PathVariable String code) {
        boolean isSuccess = service.confirmEmail(code);
        if (isSuccess) {
            UserConfirmationResponse response = new UserConfirmationResponse("User and email verify successfully.");
            return ResponseEntity.ok(response);
        }
        throw new InvalidConfirmationException(HttpStatus.UNAUTHORIZED, "Validation was not possible, the token or user is not valid.");
    }
}
