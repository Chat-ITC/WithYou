package WithYou.domain.ai.repository;

import WithYou.domain.ai.entity.AiSummaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<AiSummaryContent, Long> {

}
