package cl.tica.portfolio.recipeapi.models;

import java.util.Date;

public record ExceptionWrappingError(Date timespan, String title, String message, int status) {
}
