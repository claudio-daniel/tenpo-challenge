package cl.tenpo.rest.api.controller.advice;

import cl.tenpo.rest.api.model.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var errorsDetail = exception.getBindingResult().getAllErrors()
                .stream()
                .map(error -> ErrorDetail.builder()
                        .code(Objects.requireNonNull(error.getCodes())[1])
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        var error = ErrorResponse.builder()
                .reason(exception.getBody().getDetail())
                .statusCode(HttpStatus.BAD_REQUEST.toString())
                .timestamp(ZonedDateTime.now().toString())
                .errorsDetail(errorsDetail)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({ApiBaseException.class})
    public ResponseEntity<ErrorResponse> handleException(ApiBaseException exception, WebRequest request) {
        var errorsDetail = Collections.singletonList(exception.getApiError());

        var error = ErrorResponse.builder()
                .reason(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(ZonedDateTime.now().toString())
                .errorsDetail(errorsDetail)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({ApiBadRequestException.class})
    public ResponseEntity<ErrorResponse> handleException(ApiBadRequestException exception, WebRequest request) {
        var errorsDetail = Collections.singletonList(exception.getApiError());

        var error = ErrorResponse.builder()
                .reason(exception.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.toString())
                .timestamp(ZonedDateTime.now().toString())
                .errorsDetail(errorsDetail)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({RequestLimitExceededException.class})
    public ResponseEntity<ErrorResponse> handleException(RequestLimitExceededException exception, WebRequest request) {
        var errorsDetail = Collections.singletonList(exception.getApiError());

        var error = ErrorResponse.builder()
                .reason(exception.getMessage())
                .statusCode(HttpStatus.TOO_MANY_REQUESTS.toString())
                .timestamp(ZonedDateTime.now().toString())
                .errorsDetail(errorsDetail)
                .build();

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(error);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exception, WebRequest request) {
        var error = ErrorResponse.builder()
                .reason(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(ZonedDateTime.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}