package WithYou.domain.post.dto.request;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRegistDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .userGrade(member.getGrade())
                .userMajor(member.getMajor())
                .userNickName(member.getNickName())
                .build();
    }
}
