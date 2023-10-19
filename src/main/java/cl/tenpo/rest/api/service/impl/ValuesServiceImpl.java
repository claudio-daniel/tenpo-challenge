package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.client.ExternalServiceClient;
import cl.tenpo.rest.api.model.entity.PercentageValueHistory;
import cl.tenpo.rest.api.model.exception.ApiBaseException;
import cl.tenpo.rest.api.model.request.CalculateValueRequest;
import cl.tenpo.rest.api.model.response.CalculatedValueResponse;
import cl.tenpo.rest.api.service.PercentageHistoryService;
import cl.tenpo.rest.api.service.ValuesService;
import org.springframework.stereotype.Service;

@Service
public class ValuesServiceImpl implements ValuesService {

    private final ExternalServiceClient externalServiceClient;
    private final PercentageHistoryService percentageHistoryRepository;

    public ValuesServiceImpl(ExternalServiceClient externalServiceClient, PercentageHistoryService percentageHistoryService) {
        this.externalServiceClient = externalServiceClient;
        this.percentageHistoryRepository = percentageHistoryService;
    }

    @Override
    public CalculatedValueResponse sumAndApplyPercentage(CalculateValueRequest calculateValueRequest) {

        var valueResponse = calculateValueResponse(calculateValueRequest.getFirstValue(), calculateValueRequest.getSecondValue());

        return CalculatedValueResponse.builder()
                .value(valueResponse.toString())
                .build();
    }

    private Double calculateValueResponse(Long firstValue, Long secondValue) {
        var percentageValue = findPercentageValue();
        var sumValues = firstValue.doubleValue() + secondValue.doubleValue();

        return sumValues + (sumValues / 100 * percentageValue);
    }

    private Integer findPercentageValue() {
        try {
            return percentageHistoryRepository.findLastPercentageReceived()
                    .map(PercentageValueHistory::getPercentageValue)
                    .orElseGet(this::getPercentageFromWebClient);
        } catch (ApiBaseException clientException) {
            return percentageHistoryRepository.findLastPercentageReturned()
                    .map(PercentageValueHistory::getPercentageValue)
                    .orElseThrow(() -> clientException);
        }
    }

    private Integer getPercentageFromWebClient() {
        var percentageResponse = externalServiceClient.getPercentageFromWebClient();

        percentageHistoryRepository.savePercentageHistory(percentageResponse.getPercentage());

        return percentageResponse.getPercentage();
    }
}