package cl.tenpo.rest.api.controller;

import cl.tenpo.rest.api.model.request.RequestHistoryParams;
import cl.tenpo.rest.api.service.RequestHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static cl.tenpo.rest.api.mock.request.RequestHistoryMock.mockPageRequestHistoryResponse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = RequestHistoryController.class)
public class RequestHistoryControllerTest {
    private final String requestHistoryEndpoint = "/request-history";
    private final RequestHistoryParams paramsMock = RequestHistoryParams.builder().size(1).page(1).build();

    @MockBean
    private RequestHistoryService requestHistoryService;

    @Autowired
    private MockMvc mvc;

    @Test
    void whenRequestHistoryExistShouldBeReturnRequestHistoryOK() throws Exception {

        when(requestHistoryService.findAllRequestHistory(paramsMock)).thenReturn(mockPageRequestHistoryResponse());

        this.mvc.perform(get(requestHistoryEndpoint)
                        .param("size", paramsMock.getSize().toString())
                        .param("page", paramsMock.getPage().toString())
                        .header("api-key", "request-history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(requestHistoryService, times(1)).findAllRequestHistory(paramsMock);
    }

    @Test
    void whenRequestHistoryReturnsErrorShouldBeThrowException() throws Exception {

        when(requestHistoryService.findAllRequestHistory(paramsMock)).thenThrow(new RuntimeException());

        this.mvc.perform(get(requestHistoryEndpoint)
                        .param("size", paramsMock.getSize().toString())
                        .param("page", paramsMock.getPage().toString())
                        .header("api-key", "request-history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(requestHistoryService, times(1)).findAllRequestHistory(paramsMock);
    }

    @Test
    void whenMessingApiKeyShouldBeReturnBadRequest() throws Exception {
        this.mvc.perform(get(requestHistoryEndpoint)
                        .param("size", paramsMock.getSize().toString())
                        .param("page", paramsMock.getPage().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(requestHistoryService, times(0)).findAllRequestHistory(any());
    }

    @Test
    void whenRateLimitIsExceededShouldBeReturnTooManyRequest() throws Exception {
        for (int i = 0; i <= 10; i++) {
            this.mvc.perform(get(requestHistoryEndpoint)
                    .param("size", "1")
                    .param("page", "1")
                    .header("api-key", "rate-limit-exceeded")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        this.mvc.perform(get(requestHistoryEndpoint)
                        .param("size", "1")
                        .param("page", "1")
                        .header("api-key", "rate-limit-exceeded")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isTooManyRequests());

        verify(requestHistoryService, times(10)).findAllRequestHistory(any());
    }
}