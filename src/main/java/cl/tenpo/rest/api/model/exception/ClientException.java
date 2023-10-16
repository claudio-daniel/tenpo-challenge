package cl.tenpo.rest.api.model.exception;

public abstract class ClientException extends RuntimeException {
    private final ErrorDetail apiError;

    public ClientException(String message, ErrorDetail apiError) {
        super(message);
        this.apiError = apiError;
    }

    public ErrorDetail getApiError() {
        return this.apiError;
    }
}