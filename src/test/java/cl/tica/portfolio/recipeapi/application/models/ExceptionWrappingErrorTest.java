package cl.tica.portfolio.recipeapi.application.models;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
class ExceptionWrappingErrorTest {

    @Test
    void toJSONString() {
        Exception exception = new Exception();
        ArrayList<Object> errorsList = new ArrayList<>();
        errorsList.add(exception);

        ExceptionWrappingError error = new ExceptionWrappingError(
                400,
                "ERROR_CODE",
                "Error Detail",
                "/path",
                errorsList
        );

        String json = error.toJSONString();
        String errorCode = JsonPath.parse(json).read("$.code", String.class);
        String errorDetail = JsonPath.parse(json).read("$.details", String.class);

        Assertions.assertThat(errorCode).isEqualTo("ERROR_CODE");
        Assertions.assertThat(errorDetail).isEqualTo("Error Detail");
    }

    @Test
    void toJSONStringWithOutErrorList() {
        Exception exception = new Exception();

        ExceptionWrappingError error = new ExceptionWrappingError(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                "/path"
        );

        Map<String, Object> jsonMap = JsonPath.parse(error.toJSONString()).read("$");

        Assertions.assertThat(jsonMap).doesNotContainKey("errors");
    }
}
