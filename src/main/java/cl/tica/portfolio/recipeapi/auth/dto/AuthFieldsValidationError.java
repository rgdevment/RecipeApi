package cl.tica.portfolio.recipeapi.auth.dto;

public record AuthFieldsValidationError(String field, String type, String message) {
}
