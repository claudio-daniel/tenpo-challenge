package cl.tenpo.rest.api.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class RequestHistoryParams {
    private Integer size;
    private Integer page;
}