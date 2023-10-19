package cl.tenpo.rest.api.service;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.model.request.RequestHistoryParams;
import cl.tenpo.rest.api.model.response.PageResponse;
import cl.tenpo.rest.api.model.response.RequestHistoryResponse;

public interface RequestHistoryService {

    void save(RequestHistory requestHistory);

    PageResponse<RequestHistoryResponse> findAllRequestHistory(RequestHistoryParams params);
}