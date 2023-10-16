package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.client.ExternalServiceClient;
import cl.tenpo.rest.api.model.entity.PercentageValueHistory;
import cl.tenpo.rest.api.model.request.CalculateValueRequest;
import cl.tenpo.rest.api.model.response.CalculatedValueResponse;
import cl.tenpo.rest.api.service.PercentageHistoryService;
import cl.tenpo.rest.api.service.ValuesService;
import org.springframework.stereotype.Service;

@Service
public class ValuesServiceImpl implements ValuesService {

    private final ExternalServiceClient externalServiceClient;
    private final PercentageHistoryService percentageHistoryRedisRepository;

    public ValuesServiceImpl(ExternalServiceClient externalServiceClient, PercentageHistoryService percentageHistoryService) {
        this.externalServiceClient = externalServiceClient;
        this.percentageHistoryRedisRepository = percentageHistoryService;
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
        var lastPercentageValue = percentageHistoryRedisRepository.findLastPercentageReceived();

        return lastPercentageValue
                .map(PercentageValueHistory::getPercentageValue)
                .orElseGet(this::getPercentageFromWebClient);
    }

    private Integer getPercentageFromWebClient() {
        var percentageResponse = externalServiceClient.getPercentageFromWebClient();

        percentageHistoryRedisRepository.save(percentageResponse.getPercentage());

        return percentageResponse.getPercentage();
    }
}