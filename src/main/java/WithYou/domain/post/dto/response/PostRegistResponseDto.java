package WithYou.domain.post.dto.response;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostRegistResponseDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageUrl;


    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .userGrade(member.getGrade())
                .userMajor(member.getMajor())
                .userLevel(member.getLevel())
                .userNickName(member.getNickName())
                .imageUrl(imageUrl)
                .build();
    }

}
