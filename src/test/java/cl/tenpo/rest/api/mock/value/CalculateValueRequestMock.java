package cl.tenpo.rest.api.mock.value;

import cl.tenpo.rest.api.model.request.CalculateValueRequest;

public class CalculateValueRequestMock {
    public static CalculateValueRequest mockCalculateValueRequest() {
        return CalculateValueRequest.builder()
                .firstValue(12L)
                .secondValue(24L)
                .build();
    }

    public static String mockCalculateValueRequestJson() {
        return """
                {
                    "first_value": "12",
                    "second_value": "24"
                }
                """;
    }
}