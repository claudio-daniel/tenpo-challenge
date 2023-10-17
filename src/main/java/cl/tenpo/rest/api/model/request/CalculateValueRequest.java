package cl.tenpo.rest.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateValueRequest {

    @NotNull(message = "Cannot be null")
    @JsonProperty("first_value")
    private Long firstValue;

    @NotNull(message = "Cannot be null")
    @JsonProperty("second_value")
    private Long secondValue;
}