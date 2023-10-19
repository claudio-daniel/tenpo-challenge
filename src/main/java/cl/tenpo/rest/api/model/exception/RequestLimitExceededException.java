package cl.tenpo.rest.api.model.exception;

public class RequestLimitExceededException extends ApiBaseException {
    public RequestLimitExceededException(String message, ErrorDetail apiError) {
        super(message, apiError);
    }
}