package cl.tica.portfolio.recipeapi.application.models;

import cl.tica.portfolio.recipeapi.application.dto.ValidationFieldsError;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExceptionWrappingError {

    private final LocalDateTime timespan;
    private final String code;
    private final int status;
    private final String details;
    private final String instance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationFieldsError> errors;

    public ExceptionWrappingError(int status, String code, String details, String instance, List<ValidationFieldsError> errors) {
        this.timespan = LocalDateTime.now();
        this.status = status;
        this.code = code;
        this.details = details;
        this.instance = instance;
        this.errors = errors;
    }

    public ExceptionWrappingError(int status, String code, String details, String instance) {
        this(status, code, details, instance, new ArrayList<>());
    }

    public LocalDateTime getTimespan() {
        return timespan;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

    public String getInstance() {
        return instance;
    }

    public List<ValidationFieldsError> getErrors() {
        return errors;
    }

    public String toJSONString() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();
        node.put("timespan", getTimespan().toString());
        node.put("status", getStatus());
        node.put("code", getCode());
        node.put("details", getDetails());
        node.put("instance", getInstance());

        if (!getErrors().isEmpty()) {
            ArrayNode errorsNode = mapper.valueToTree(getErrors());
            node.set("errors", errorsNode);
        }

        return node.toString();
    }

}
