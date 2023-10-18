package cl.tenpo.rest.api.controller.advice;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.service.RequestHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@ControllerAdvice
@Slf4j
public class RequestHistoryControllerAdvice implements ResponseBodyAdvice<Object> {

    private final RequestHistoryService requestHistoryService;

    public RequestHistoryControllerAdvice(RequestHistoryService requestHistoryService) {
        this.requestHistoryService = requestHistoryService;
    }

    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (serverHttpRequest instanceof ServletServerHttpRequest && serverHttpResponse instanceof ServletServerHttpResponse) {
            CompletableFuture.runAsync(() -> processRequestInfo(serverHttpRequest, serverHttpResponse, responseBody));
        }
        return responseBody;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Async
    private void processRequestInfo(ServerHttpRequest httpServletRequest, ServerHttpResponse httpServletResponse, Object responseBody) {
        try {
            log.info("RESPONSE " +
                    "method=[" + httpServletRequest.getMethod() + "] " +
                    "path=[" + httpServletRequest.getURI() + "] " +
                    "responseBody=[" + responseBody + "] ");

            var request = ((ServletServerHttpRequest) httpServletRequest).getServletRequest();
            var response = ((ServletServerHttpResponse) httpServletResponse).getServletResponse();

            if (HttpStatus.valueOf(response.getStatus()).is2xxSuccessful()) {
                var requestHistory = RequestHistory.builder()
                        .requestId(request.getRequestId())
                        .url(request.getRequestURI())
                        .httpMethod(request.getMethod())
                        .responseStatus(response.getStatus())
                        .responseBody(responseBody.toString())
                        .timestamp(ZonedDateTime.now())
                        .build();

                requestHistoryService.save(requestHistory);
            }
        } catch (Exception exception) {
            log.error("Request history cannot be save. ", exception);
        }
    }
}