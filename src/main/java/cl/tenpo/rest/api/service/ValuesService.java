package cl.tenpo.rest.api.service;

import cl.tenpo.rest.api.model.request.CalculateValueRequest;
import cl.tenpo.rest.api.model.response.CalculatedValueResponse;

public interface ValuesService {
    CalculatedValueResponse sumAndApplyPercentage(CalculateValueRequest calculateValueRequest);
}