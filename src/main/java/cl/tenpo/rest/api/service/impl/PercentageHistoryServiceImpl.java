package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.model.entity.PercentageValueHistory;
import cl.tenpo.rest.api.repository.redis.PercentageHistoryRepository;
import cl.tenpo.rest.api.service.PercentageHistoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PercentageHistoryServiceImpl implements PercentageHistoryService {
    private final String lastPercentageKey = "last-percentage-received";

    private final PercentageHistoryRepository percentageHistoryRepository;

    public PercentageHistoryServiceImpl(PercentageHistoryRepository percentageHistoryRepository) {
        this.percentageHistoryRepository = percentageHistoryRepository;
    }

    @Override
    public void save(Integer percentageValue) {
        percentageHistoryRepository.save(PercentageValueHistory.builder()
                .id(lastPercentageKey)
                .percentageValue(percentageValue)
                .build());
    }

    @Override
    public Optional<PercentageValueHistory> findLastPercentageReceived() {
        return percentageHistoryRepository.findById(lastPercentageKey);
    }
}