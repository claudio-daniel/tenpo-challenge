package cl.tenpo.rest.api.mock.request;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.model.response.PageResponse;
import cl.tenpo.rest.api.model.response.RequestHistoryResponse;

import java.time.ZonedDateTime;
import java.util.Collections;

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

    public static RequestHistoryResponse mockRequestHistoryResponse() {
        return RequestHistoryResponse.builder()
                .requestId("1")
                .httpMethod("POST")
                .responseStatus(200)
                .responseBody("ResponseBody")
                .url("tenpo/api-rest")
                .timestamp(ZonedDateTime.now())
                .build();
    }

    public static PageResponse<RequestHistoryResponse> mockPageRequestHistoryResponse() {
       return PageResponse.<RequestHistoryResponse>builder()
               .content(Collections.singletonList(mockRequestHistoryResponse()))
               .page(1)
               .size(2)
               .totalElements(10L)
               .totalPages(5)
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