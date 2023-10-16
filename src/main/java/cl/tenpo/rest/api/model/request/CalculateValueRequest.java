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

    @NotNull
    @JsonProperty("first_value")
    private Long firstValue;

    @NotNull
    @JsonProperty("second_value")
    private Long secondValue;
}