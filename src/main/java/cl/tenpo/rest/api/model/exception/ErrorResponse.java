package cl.tenpo.rest.api.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String statusCode;
    private String reason;
    private String timestamp;

    private List<ErrorDetail> errorsDetail;
}
