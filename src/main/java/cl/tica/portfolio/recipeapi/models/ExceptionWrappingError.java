package cl.tica.portfolio.recipeapi.models;

import java.util.Date;
import java.util.List;

public record ExceptionWrappingError(Date timespan,
                                     String title,
                                     int status,
                                     String detail,
                                     String instance,
                                     List<?> errors) {
}
