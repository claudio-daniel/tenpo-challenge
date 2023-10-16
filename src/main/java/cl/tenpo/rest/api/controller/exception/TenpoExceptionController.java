package cl.tenpo.rest.api.controller.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenpoExceptionController {

    @ExceptionHandler({ResourceAccessException.class})
    public ResponseEntity<ErrorResponse> handleException(ResourceAccessException exception, WebRequest request) {
        var error = ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, "exception.getLocalizedMessage()")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
