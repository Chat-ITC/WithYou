package WithYou.domain.ai.repository;

import WithYou.domain.ai.entity.AiSummaryContent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<AiSummaryContent, Long> {
    Optional<AiSummaryContent> findAiSummaryContentById(Long id);
}
