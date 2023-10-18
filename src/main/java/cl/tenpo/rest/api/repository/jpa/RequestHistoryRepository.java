package cl.tenpo.rest.api.repository.jpa;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {
}
