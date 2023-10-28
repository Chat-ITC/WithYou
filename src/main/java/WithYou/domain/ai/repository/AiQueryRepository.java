package WithYou.domain.ai.repository;

import static WithYou.domain.ai.entity.QAiSummaryContent.aiSummaryContent;

import WithYou.domain.ai.entity.AiSummaryContent;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AiQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<AiSummaryContent> findAiSummaryContentList(Long id, Pageable pageable) {
        JPAQuery<AiSummaryContent> query = jpaQueryFactory
                .selectFrom(aiSummaryContent)
                .where(aiSummaryContent.id.eq(id));

        return PageableExecutionUtils.getPage(
                query.fetch(), // 쿼리 결과 리스트
                pageable, // 페이지 정보
                () -> query.fetchCount() // 총 결과 수를 계산하는 로직
        );
    }


}
