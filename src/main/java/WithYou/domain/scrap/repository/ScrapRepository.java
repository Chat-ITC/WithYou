package WithYou.domain.scrap.repository;

import WithYou.domain.scrap.entity.ScrapContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<ScrapContent, Long> {

}
