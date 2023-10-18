package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.repository.jpa.RequestHistoryRepository;
import cl.tenpo.rest.api.service.RequestHistoryService;
import org.springframework.stereotype.Service;

@Service
public class RequestHistoryServiceImpl implements RequestHistoryService {

    private final RequestHistoryRepository requestHistoryRepository;

    public RequestHistoryServiceImpl(RequestHistoryRepository requestHistoryRepository) {
        this.requestHistoryRepository = requestHistoryRepository;
    }

    @Override
    public void save(RequestHistory requestHistory) {
        requestHistoryRepository.save(requestHistory);
    }
}