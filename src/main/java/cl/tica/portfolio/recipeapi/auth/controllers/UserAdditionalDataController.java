package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.request.AdditionalDataRequest;
import cl.tica.portfolio.recipeapi.auth.dto.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.dto.response.UserConfirmationResponse;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserAdditionalData;
import cl.tica.portfolio.recipeapi.auth.services.UserAdditionalDataService;
import cl.tica.portfolio.recipeapi.models.ExceptionWrappingError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Additional Data User Controller")
@RestController
@RequestMapping("/v1/user")
public class UserAdditionalDataController {
    private final UserAdditionalDataService userAdditionalDataService;

    public UserAdditionalDataController(UserAdditionalDataService userAdditionalDataService) {
        this.userAdditionalDataService = userAdditionalDataService;
    }

    @Operation(summary = "Add Additional user data information.")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserConfirmationResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrappingError.class)))
    @PutMapping("/additional-data")
    public ResponseEntity<UserConfirmationResponse> addUserAdditionalData(@Valid @RequestBody AdditionalDataRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userAdditionalDataService.findUserByUsername(auth.getName());
        UserAdditionalData userAdditionalData = user.getUserData();
        userAdditionalData.setName(request.name());
        userAdditionalData.setLastname(request.lastname());
        userAdditionalData.setGender(request.gender());

        userAdditionalDataService.updateUserData(user);

        return ResponseEntity.ok(new UserConfirmationResponse("Additional user information updated successfully."));
    }
}
