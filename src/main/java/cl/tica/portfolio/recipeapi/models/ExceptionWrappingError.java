package cl.tica.portfolio.recipeapi.models;

public record ExceptionWrappingError(String title, String message, int status) {
}
