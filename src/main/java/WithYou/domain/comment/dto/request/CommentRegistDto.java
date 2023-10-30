package WithYou.domain.comment.dto.request;

import WithYou.domain.comment.entity.Comment;
import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRegistDto {
    @NotNull
    private String content;

    public Comment of(Member member, Post post) {
        return Comment.builder()
                .content(content)
                .userNickName(member.getNickName())
                .userMajor(member.getMajor())
                .userGrade(member.getGrade())
                .post(post)
                .build();
    }
}
