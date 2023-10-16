package cl.tenpo.rest.api.mock.value;

import cl.tenpo.rest.api.client.model.response.PercentageResponse;
import cl.tenpo.rest.api.model.entity.PercentageValueHistory;

public class PercentageMock {
    public static PercentageResponse mockPercentageResponse() {
        return PercentageResponse.builder()
                .percentage(10)
                .build();
    }

    public static PercentageValueHistory mockPercentageValueHistory() {
        return PercentageValueHistory.builder()
                .id("last-percentage-received")
                .percentageValue(10)
                .build();
    }
}