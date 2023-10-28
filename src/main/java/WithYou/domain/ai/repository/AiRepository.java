package WithYou.domain.ai.repository;

import WithYou.domain.ai.entity.AiSummaryContent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<AiSummaryContent, Long> {
    public Optional<AiSummaryContent> findById(Long id);
}
