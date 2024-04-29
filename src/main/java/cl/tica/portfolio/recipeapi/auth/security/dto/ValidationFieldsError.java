package cl.tica.portfolio.recipeapi.auth.security.dto;

public record ValidationFieldsError(String field, String type, String message) {
}
