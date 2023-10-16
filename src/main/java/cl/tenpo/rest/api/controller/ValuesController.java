package cl.tenpo.rest.api.controller;


import cl.tenpo.rest.api.model.request.CalculateValueRequest;
import cl.tenpo.rest.api.model.response.CalculatedValueResponse;
import cl.tenpo.rest.api.service.ValuesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tenpo/api-rest/values")
public class ValuesController {

    private final ValuesService valuesService;

    public ValuesController(ValuesService valuesService) {
        this.valuesService = valuesService;
    }

    @PostMapping("calculate")
    public CalculatedValueResponse sumAndApplyPercentage(@Validated @RequestBody CalculateValueRequest tenpoCalculateValueRequest) {
        return valuesService.sumAndApplyPercentage(tenpoCalculateValueRequest);
    }
}