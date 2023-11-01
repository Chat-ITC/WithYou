package WithYou.domain.scrap.repository;

import WithYou.domain.scrap.entity.Scrap;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findScrapByPostId(Long postId);
}
