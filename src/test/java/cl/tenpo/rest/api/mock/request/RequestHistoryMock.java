package cl.tenpo.rest.api.mock.request;

import cl.tenpo.rest.api.model.entity.RequestHistory;

import java.time.ZonedDateTime;

public class RequestHistoryMock {
    public static RequestHistory mockRequestHistory() {
        return mockFieldsRequestHistory()
                .build();
    }

    public static RequestHistory mockRequestHistoryWithId() {
        return mockFieldsRequestHistory()
                .id(11L)
                .build();
    }

    private static RequestHistory.RequestHistoryBuilder mockFieldsRequestHistory() {
        return RequestHistory.builder()
                .id(11L)
                .requestId("1")
                .httpMethod("POST")
                .url("/tenpo/endpoint")
                .responseBody("responseBody")
                .responseStatus(200)
                .timestamp(ZonedDateTime.now());
    }
}