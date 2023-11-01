package WithYou.domain.scrap;

import WithYou.domain.member.entity.Member;
import WithYou.domain.scrap.entity.Scrap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findScrapByMemberId(Long memberId);

    Optional<Scrap> findScrapByPostId(Long postId);
}
