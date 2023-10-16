package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.client.ExternalServiceClient;
import cl.tenpo.rest.api.service.ValuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import static cl.tenpo.rest.api.mock.value.CalculateValueRequestMock.mockCalculateValueRequest;
import static cl.tenpo.rest.api.mock.value.PercentageResponseMock.mockPercentageResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValuesServiceImplTest {

    @Mock
    private ExternalServiceClient externalServiceClient;

    private ValuesService valuesService;

    @BeforeEach
    void setup() {
        this.valuesService = new ValuesServiceImpl(externalServiceClient);
    }

    @Test
    void whenSumAndApplyPercentageIsCalledThenReturnValueResponseOk() {

        when(externalServiceClient.getPercentageFromWebClient()).thenReturn(mockPercentageResponse());

        var calculatedValueResponse = valuesService.sumAndApplyPercentage(mockCalculateValueRequest());

        assertNotNull(calculatedValueResponse);
        verify(externalServiceClient, times(1)).getPercentageFromWebClient();
    }

    @Test
    void whenExternalServiceFailThenReturnInternalServerError() {

        when(externalServiceClient.getPercentageFromWebClient()).thenThrow(ResourceAccessException.class);

        assertThrows(ResourceAccessException.class, () -> valuesService.sumAndApplyPercentage(mockCalculateValueRequest()));
        verify(externalServiceClient, times(1)).getPercentageFromWebClient();
    }
}