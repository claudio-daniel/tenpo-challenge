package cl.tenpo.rest.api.mapper;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.model.response.RequestHistoryResponse;
import org.mapstruct.Mapper;

import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RequestHistoryMapper {
    public abstract RequestHistoryResponse toRequestHistoryResponse(RequestHistory requestHistory);
}