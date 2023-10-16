package cl.tenpo.rest.api.service;

import cl.tenpo.rest.api.model.entity.PercentageValueHistory;

import java.util.Optional;

public interface PercentageHistoryService {
    void save(Integer percentageValue);

    Optional<PercentageValueHistory> findLastPercentageReceived();
}