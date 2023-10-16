package cl.tenpo.rest.api.controller;

import cl.tenpo.rest.api.model.exception.ClientException;
import cl.tenpo.rest.api.model.exception.ErrorDetail;
import cl.tenpo.rest.api.model.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception, WebRequest request) {

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

    @ExceptionHandler({ClientException.class})
    public ResponseEntity<ErrorResponse> handleException(ClientException exception, WebRequest request) {
        var errorsDetail = Collections.singletonList(exception.getApiError());

        var error = ErrorResponse.builder()
                .reason(exception.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(ZonedDateTime.now().toString())
                .errorsDetail(errorsDetail)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}