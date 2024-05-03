package cl.tica.portfolio.recipeapi.application.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExceptionWrappingError {

    private final Date timespan;
    private final String title;
    private final int status;
    private final String detail;
    private final String instance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<?> errors;

    public ExceptionWrappingError(String title, int status, String detail, String instance, List<?> errors) {
        this.timespan = new Date(System.currentTimeMillis());
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.errors = errors;
    }

    public ExceptionWrappingError(String title, int status, String detail, String instance) {
        this(title, status, detail, instance, new ArrayList<>());
    }

    public Date getTimespan() {
        return timespan;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstance() {
        return instance;
    }

    public List<?> getErrors() {
        return errors;
    }

    public String toJSONString() {
        StringBuilder jsonBuilder = new StringBuilder("{"
                + "\"timespan\": \"" + getTimespan() + "\","
                + "\"title\": \"" + getTitle() + "\","
                + "\"status\": " + getStatus() + ","
                + "\"detail\": \"" + getDetail() + "\","
                + "\"instance\": \"" + getInstance() + "\"");

        if (!getErrors().isEmpty()) {
            jsonBuilder.append(",\"errors\": ").append(getErrors());
        }

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

}
