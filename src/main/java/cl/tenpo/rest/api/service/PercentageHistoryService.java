package cl.tenpo.rest.api.service;

import cl.tenpo.rest.api.model.entity.PercentageValueHistory;

import java.util.Optional;

public interface PercentageHistoryService {
    void savePercentageHistory(Integer percentageValue);

    Optional<PercentageValueHistory> findLastPercentageReceived();

    Optional<PercentageValueHistory> findLastPercentageReturned();
}