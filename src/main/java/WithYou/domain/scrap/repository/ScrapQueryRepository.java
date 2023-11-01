package WithYou.domain.scrap.repository;

import WithYou.domain.member.entity.Member;
import WithYou.domain.scrap.entity.QScrap;
import WithYou.domain.scrap.entity.Scrap;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScrapQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Scrap> findScrapByMemberIdAndVerify(Member member, Long postId) {
        Scrap scrap = jpaQueryFactory
                .selectFrom(QScrap.scrap)
                .where(QScrap.scrap.member.eq(member).and(QScrap.scrap.postId.eq(postId)))
                .fetchOne();

        return Optional.ofNullable(scrap);
    }
}
