package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.client.ExternalServiceClient;
import cl.tenpo.rest.api.service.PercentageHistoryService;
import cl.tenpo.rest.api.service.ValuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

import static cl.tenpo.rest.api.mock.value.CalculateValueRequestMock.mockCalculateValueRequest;
import static cl.tenpo.rest.api.mock.value.PercentageMock.mockPercentageResponse;
import static cl.tenpo.rest.api.mock.value.PercentageMock.mockPercentageValueHistory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValuesServiceImplTest {

    @Mock
    private ExternalServiceClient externalServiceClient;

    @Mock
    private PercentageHistoryService percentageHistoryService;

    private ValuesService valuesService;

    @BeforeEach
    void setup() {
        this.valuesService = new ValuesServiceImpl(externalServiceClient, percentageHistoryService);
    }

    @Test
    void whenThereIsNoDataInCacheThenReturnValueFromExternalService() {
        when(percentageHistoryService.findLastPercentageReceived()).thenReturn(Optional.empty());
        when(externalServiceClient.getPercentageFromWebClient()).thenReturn(mockPercentageResponse());

        var calculatedValueResponse = valuesService.sumAndApplyPercentage(mockCalculateValueRequest());

        assertNotNull(calculatedValueResponse);
        verify(externalServiceClient, times(1)).getPercentageFromWebClient();
        verify(percentageHistoryService, times(1)).findLastPercentageReceived();
    }

    @Test
    void whenExistDataInCacheThenReturnResponseFromCache() {
        when(percentageHistoryService.findLastPercentageReceived()).thenReturn(Optional.of(mockPercentageValueHistory()));

        var calculatedValueResponse = valuesService.sumAndApplyPercentage(mockCalculateValueRequest());

        assertNotNull(calculatedValueResponse);
        verify(externalServiceClient, times(0)).getPercentageFromWebClient();
        verify(percentageHistoryService, times(1)).findLastPercentageReceived();
    }

    @Test
    void whenExternalServiceFailThenReturnInternalServerError() {
        when(percentageHistoryService.findLastPercentageReceived()).thenReturn(Optional.empty());
        when(externalServiceClient.getPercentageFromWebClient()).thenThrow(ResourceAccessException.class);

        assertThrows(ResourceAccessException.class, () -> valuesService.sumAndApplyPercentage(mockCalculateValueRequest()));
        verify(externalServiceClient, times(1)).getPercentageFromWebClient();
        verify(percentageHistoryService, times(1)).findLastPercentageReceived();
    }
}