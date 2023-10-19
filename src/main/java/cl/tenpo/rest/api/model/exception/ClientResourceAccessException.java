package cl.tenpo.rest.api.model.exception;

public class ClientResourceAccessException extends ApiBaseException {
    private static final String CODE = "REQUEST_NOT_COMPLETE";
    private static final String ERROR_MESSAGE = "Could not complete request to client";

    public ClientResourceAccessException(String message) {
        super(ERROR_MESSAGE, ErrorDetail.builder()
                .code(CODE)
                .message(message)
                .build());
    }
}