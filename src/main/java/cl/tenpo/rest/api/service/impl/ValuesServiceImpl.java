package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.client.ExternalServiceClient;
import cl.tenpo.rest.api.model.request.CalculateValueRequest;
import cl.tenpo.rest.api.model.response.CalculatedValueResponse;
import cl.tenpo.rest.api.service.ValuesService;
import org.springframework.stereotype.Service;

@Service
public class ValuesServiceImpl implements ValuesService {

    private final ExternalServiceClient externalServiceClient;

    public ValuesServiceImpl(ExternalServiceClient externalServiceClient) {
        this.externalServiceClient = externalServiceClient;
    }

    @Override
    public CalculatedValueResponse sumAndApplyPercentage(CalculateValueRequest calculateValueRequest) {

        var valueResponse = calculateValueResponse(calculateValueRequest.getFirstValue(), calculateValueRequest.getSecondValue());

        return CalculatedValueResponse.builder()
                .value(valueResponse.toString())
                .build();
    }

    private Double calculateValueResponse(Long firstValue, Long secondValue) {
        var percentageResponse = externalServiceClient.getPercentageFromWebClient();
        var sumValues = firstValue.doubleValue() + secondValue.doubleValue();

        return sumValues + (sumValues / 100 * percentageResponse.getPercentage().doubleValue());
    }
}