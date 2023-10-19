package cl.tenpo.rest.api.model.exception;

public class ApiBadRequestException extends ApiBaseException {
    public ApiBadRequestException(String message, ErrorDetail apiError) {
        super(message, apiError);
    }
}