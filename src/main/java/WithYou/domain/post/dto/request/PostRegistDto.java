package WithYou.domain.post.dto.request;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRegistDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public PostRegistDto(PostRegistDto postRegistDto, String imageUrl) {
        this.content = postRegistDto.content;
        this.title = postRegistDto.title;
    }

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
