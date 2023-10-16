package cl.tenpo.rest.api.mock.value;

import cl.tenpo.rest.api.client.model.response.PercentageResponse;

public class PercentageResponseMock {
    public static PercentageResponse mockPercentageResponse() {
        return PercentageResponse.builder()
                .percentage(10)
                .build();
    }
}