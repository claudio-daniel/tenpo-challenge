package cl.tenpo.rest.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CalculatedValueResponse {
    private String value;
}
