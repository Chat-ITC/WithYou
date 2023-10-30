package WithYou.domain.post.dto.request;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.entity.UserInfo;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRegistDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public Post toEntity(Member member) {
        UserInfo userInfo = UserInfo.builder()
                .userNickName(member.getNickName())
                .userMajor(member.getMajor())
                .userGrade(member.getGrade())
                .build();
        return Post.builder()
                .title(title)
                .content(content)
                .userInfo(userInfo)
                .build();
    }
}
