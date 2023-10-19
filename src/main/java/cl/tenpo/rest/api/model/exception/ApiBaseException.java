package cl.tenpo.rest.api.model.exception;

public abstract class ApiBaseException extends RuntimeException {
    private final ErrorDetail apiError;

    public ApiBaseException(String message, ErrorDetail apiError) {
        super(message);
        this.apiError = apiError;
    }

    public ErrorDetail getApiError() {
        return this.apiError;
    }
}