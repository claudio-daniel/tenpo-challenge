package cl.tenpo.rest.api.client;

import cl.tenpo.rest.api.client.model.response.PercentageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static cl.tenpo.rest.api.mock.value.PercentageMock.mockPercentageResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
public class ExternalServiceClientTest {

    private final String externalServiceUrl = "http://localhost:8080";
    private final String externalServiceUrn = "/percentage";

    private final URI uri = UriComponentsBuilder
            .fromUriString(this.externalServiceUrl + this.externalServiceUrn)
            .buildAndExpand()
            .toUri();

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private RetryTemplate retryTemplate;

    @InjectMocks
    private ExternalServiceClient externalServiceClient;

    @BeforeEach
    void setup() {
        setField(externalServiceClient, "externalServiceUrl", externalServiceUrl);
        setField(externalServiceClient, "externalServiceUrn", externalServiceUrn);
    }

    @Test
    void whenGetPercentageFromWebClientIsOkThenReturnValueResponse() {
        when(restTemplate.getForEntity(uri, PercentageResponse.class)).thenReturn(ResponseEntity.ok(mockPercentageResponse()));

        var percentageFromWebClient = externalServiceClient.getPercentageFromWebClient();

        assertTrue(Objects.nonNull(percentageFromWebClient));
        verify(restTemplate, times(1)).getForEntity(uri, PercentageResponse.class);

    }

    @Test
    void whenWebClientReturnsErrorThenRetryOrThrowException() {
        when(restTemplate.getForEntity(uri, PercentageResponse.class)).thenThrow(ResourceAccessException.class);

        assertThrows(RuntimeException.class, () -> externalServiceClient.getPercentageFromWebClient());
        verify(restTemplate, times(3)).getForEntity(uri, PercentageResponse.class);
    }
}