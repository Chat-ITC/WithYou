package WithYou.domain.member.repository;

import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.QAiSummaryContent;
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
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    public Page<AiSummaryContent> findAiSummaryContentList(Pageable pageable) {
        JPAQuery<AiSummaryContent> query = jpaQueryFactory
                .selectFrom(QAiSummaryContent.aiSummaryContent) // 별칭 설정
                .offset(pageable.getOffset()) // 페이지네이션 설정
                .limit(pageable.getPageSize());

        QueryResults<AiSummaryContent> results = query.fetchResults(); // 쿼리 실행

        List<AiSummaryContent> contentList = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(contentList, pageable, total);
    }
}
