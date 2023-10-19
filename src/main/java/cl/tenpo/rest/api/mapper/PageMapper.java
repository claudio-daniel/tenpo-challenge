package cl.tenpo.rest.api.mapper;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.model.response.PageResponse;
import cl.tenpo.rest.api.model.response.RequestHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PageMapper {

    private RequestHistoryMapper requestHistoryMapper;

    @Autowired
    public void setRequestHistoryMapper(RequestHistoryMapper requestHistoryMapper) {
        this.requestHistoryMapper = requestHistoryMapper;
    }

    @Mapping(target = "content", source = "content", qualifiedByName = "mapContent")
    @Mapping(target = "page", source = "number")
    @Mapping(target = "size", source = "pageable.pageSize")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "totalPages", source = "totalPages")
    public abstract PageResponse<RequestHistoryResponse> toRequestHistoryResponse(Page<RequestHistory> requestHistory);

    @Named("mapContent")
    List<RequestHistoryResponse> mapContent(List<RequestHistory> requestHistoryList) {
        return requestHistoryList.stream()
                .map(requestHistoryMapper::toRequestHistoryResponse)
                .toList();
    }
}