package WithYou.domain.post.repository;

import static WithYou.domain.post.entity.QPost.post;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Post> findPostByMemberGrade(Pageable pageable, Member member) {
        JPAQuery<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .where(post.userMajor.eq(member.getMajor()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        QueryResults<Post> results = posts.fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }


}
