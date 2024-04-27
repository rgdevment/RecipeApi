package cl.tica.portfolio.recipeapi.auth.dto;

public record ValidationFieldsError(String field, String type, String message) {
}
