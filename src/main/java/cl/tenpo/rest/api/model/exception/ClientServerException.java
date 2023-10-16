package cl.tenpo.rest.api.model.exception;

public class ClientServerException extends ClientException {
    private static final String CODE = "WEB_CLIENT_SERVER_ERROR";
    private static final String ERROR_MESSAGE = "Web client server error";

    public ClientServerException(String message) {
        super(ERROR_MESSAGE, ErrorDetail.builder()
                .code(CODE)
                .message(message)
                .build());
    }
}