package cl.tica.portfolio.recipeapi.models;

import java.util.Date;
import java.util.List;

public record ExceptionWrappingError(Date timespan, String title, String message, int status, List<?> errors) {
}
