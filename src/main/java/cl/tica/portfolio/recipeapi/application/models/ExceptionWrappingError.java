package cl.tica.portfolio.recipeapi.application.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExceptionWrappingError {

    private final Date timespan;
    private final String code;
    private final int status;
    private final String detail;
    private final String instance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<?> errors;

    public ExceptionWrappingError(int status, String code, String detail, String instance, List<?> errors) {
        this.timespan = new Date(System.currentTimeMillis());
        this.status = status;
        this.code = code;
        this.detail = detail;
        this.instance = instance;
        this.errors = errors;
    }

    public ExceptionWrappingError(int status, String code, String detail, String instance) {
        this(status, code, detail, instance, new ArrayList<>());
    }

    public Date getTimespan() {
        return timespan;
    }

    public String getCode() {
        return code;
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
                + "\"status\": " + getStatus() + ","
                + "\"code\": \"" + getCode() + "\","
                + "\"detail\": \"" + getDetail() + "\","
                + "\"instance\": \"" + getInstance() + "\"");

        if (!getErrors().isEmpty()) {
            jsonBuilder.append(",\"errors\": ").append(getErrors());
        }

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

}
