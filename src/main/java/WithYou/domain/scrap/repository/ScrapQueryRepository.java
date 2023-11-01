package WithYou.domain.scrap.repository;

import static WithYou.domain.scrap.entity.QScrap.scrap;

import WithYou.domain.member.entity.Member;
import WithYou.domain.scrap.entity.QScrap;
import WithYou.domain.scrap.entity.Scrap;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
                .orderBy(QScrap.scrap.id.desc())
                .fetchOne();

        return Optional.ofNullable(scrap);
    }

    public List<Scrap> findScrapByMemberIdOrderByIdDESC(Member member) {
        List<Scrap> scrapList = (List<Scrap>) jpaQueryFactory
                .selectFrom(scrap)
                .where(scrap.member.eq(member))
                .orderBy(scrap.id.desc())
                .fetchAll();
        return scrapList;
    }
}
