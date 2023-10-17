package cl.tenpo.rest.api.controller;


import cl.tenpo.rest.api.model.request.CalculateValueRequest;
import cl.tenpo.rest.api.model.response.CalculatedValueResponse;
import cl.tenpo.rest.api.service.ValuesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tenpo/api-rest/values")
@Tag(name = "Values", description = "Obtain result of numerical operations")
public class ValuesController {

    private final ValuesService valuesService;

    public ValuesController(ValuesService valuesService) {
        this.valuesService = valuesService;
    }

    @PostMapping("calculate")
    @Operation(
            summary = "Sum and apply percentage",
            description = "Sum two numbers and apply a percentage to the result, the percentage is obtained from an external service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculate value finishes Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public CalculatedValueResponse sumAndApplyPercentage(@Validated @RequestBody CalculateValueRequest tenpoCalculateValueRequest) {
        return valuesService.sumAndApplyPercentage(tenpoCalculateValueRequest);
    }
}