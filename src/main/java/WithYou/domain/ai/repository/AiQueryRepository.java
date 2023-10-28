package WithYou.domain.ai.repository;

import static WithYou.domain.ai.entity.QAiSummaryContent.aiSummaryContent;

import WithYou.domain.ai.entity.AiSummaryContent;
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

    public Page<AiSummaryContent> findAiSummaryContentList(Long id, Pageable pageable) {
        JPAQuery<AiSummaryContent> query = jpaQueryFactory
                .selectFrom(aiSummaryContent)
                .where(aiSummaryContent.id.eq(id));

        List<AiSummaryContent> contentList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(contentList, pageable, total);
    }

}
