package cl.tenpo.rest.api.repository.redis;

import cl.tenpo.rest.api.model.entity.PercentageValueHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercentageHistoryRepository extends CrudRepository<PercentageValueHistory, String> {
}