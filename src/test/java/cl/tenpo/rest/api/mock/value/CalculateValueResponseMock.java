package cl.tenpo.rest.api.mock.value;

import cl.tenpo.rest.api.model.response.CalculatedValueResponse;

public class CalculateValueResponseMock {
    public static CalculatedValueResponse mockCalculatedValueResponse(){
        return CalculatedValueResponse.builder()
                .value("22,5")
                .build();
    }
}