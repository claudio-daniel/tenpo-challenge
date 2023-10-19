package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.mapper.PageMapper;
import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.model.request.RequestHistoryParams;
import cl.tenpo.rest.api.model.response.PageResponse;
import cl.tenpo.rest.api.model.response.RequestHistoryResponse;
import cl.tenpo.rest.api.repository.jpa.RequestHistoryRepository;
import cl.tenpo.rest.api.service.RequestHistoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class RequestHistoryServiceImpl implements RequestHistoryService {

    private final RequestHistoryRepository requestHistoryRepository;
    private final PageMapper pageMapper;

    private static final Integer PAGE_DEFAULT_VALUE = 0;
    private static final Integer SIZE_DEFAULT_VALUE = 0;

    public RequestHistoryServiceImpl(RequestHistoryRepository requestHistoryRepository, PageMapper pageMapper) {
        this.requestHistoryRepository = requestHistoryRepository;
        this.pageMapper = pageMapper;
    }

    @Override
    public void save(RequestHistory requestHistory) {
        requestHistoryRepository.save(requestHistory);
    }

    @Override
    public PageResponse<RequestHistoryResponse> findAllRequestHistory(RequestHistoryParams params) {
        var page = isNull(params.getPage()) ? PAGE_DEFAULT_VALUE : params.getPage();
        var size = isNull(params.getSize()) ? SIZE_DEFAULT_VALUE : params.getSize();

        var requestHistoryPage = this.requestHistoryRepository.findAll(PageRequest.of(page, size));

        return pageMapper.toRequestHistoryResponse(requestHistoryPage);
    }
}