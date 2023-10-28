package WithYou.domain.member.repository;

import static WithYou.domain.member.entity.QMember.member;

import WithYou.domain.member.entity.Member;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {
    private final JPQLQueryFactory queryFactory;

    public MemberRepositorySupport(JPQLQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne());
    }
}
