package WithYou.domain.ai.repository;

import static WithYou.domain.ai.entity.QAiSummaryContent.aiSummaryContent;

import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.member.entity.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AiQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<AiSummaryContent> findAiSummaryContentList(Pageable pageable, Member member) {
        JPAQuery<AiSummaryContent> query = jpaQueryFactory
                .selectFrom(aiSummaryContent) // 별칭 설정
                .orderBy(aiSummaryContent.id.desc())
                .where(aiSummaryContent.member.eq(member))
                .offset(pageable.getOffset()) // 페이지네이션 설정
                .limit(pageable.getPageSize());

        QueryResults<AiSummaryContent> results = query.fetchResults(); // 쿼리 실행

        List<AiSummaryContent> contentList = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(contentList, pageable, total);
    }

    public Page<AiSummaryContent> findScrapContentList(Pageable pageable, Member member) {
        JPAQuery<AiSummaryContent> query = jpaQueryFactory
                .selectFrom(aiSummaryContent) // 별칭 설정
                .where(aiSummaryContent.member.eq(member).and(aiSummaryContent.isScrap.eq(IsScrap.YES)))
                .orderBy(aiSummaryContent.id.desc())
                .offset(pageable.getOffset()) // 페이지네이션 설정
                .limit(pageable.getPageSize());

        QueryResults<AiSummaryContent> results = query.fetchResults(); // 쿼리 실행

        List<AiSummaryContent> contentList = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(contentList, pageable, total);
    }
}
