package WithYou.domain.post.dto.response;

import WithYou.domain.member.entity.Member;
import WithYou.domain.post.dto.request.PostRegistDto;
import WithYou.domain.post.entity.Post;
import javax.validation.constraints.NotBlank;

public class PostRegistResponseDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageUrl;

    public PostRegistResponseDto(PostRegistDto responseDto, String imageUrl) {
        this.content = responseDto.getContent();
        this.title = responseDto.getTitle();
        this.imageUrl = imageUrl;
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
