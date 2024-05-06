package cl.tica.portfolio.recipeapi.application.dto;

public record ValidationFieldsError(String field, String type, String message) {
}
