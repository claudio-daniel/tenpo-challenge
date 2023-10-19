package cl.tenpo.rest.api.controller;

import cl.tenpo.rest.api.model.request.RequestHistoryParams;
import cl.tenpo.rest.api.model.response.PageResponse;
import cl.tenpo.rest.api.model.response.RequestHistoryResponse;
import cl.tenpo.rest.api.service.RequestHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("request-history")
@Tag(name = "Request History", description = "Return information about request received")
public class RequestHistoryController {

    private final RequestHistoryService requestHistoryService;

    public RequestHistoryController(RequestHistoryService requestHistoryService) {
        this.requestHistoryService = requestHistoryService;
    }

    @GetMapping
    @Operation(
            summary = "Get request history",
            description = "Returns a page with all calls that the microservice received with an ok response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page request history returned ok"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public PageResponse<RequestHistoryResponse> getRequestHistoryPaginated(@Validated RequestHistoryParams params) {
        return requestHistoryService.findAllRequestHistory(params);
    }
}
