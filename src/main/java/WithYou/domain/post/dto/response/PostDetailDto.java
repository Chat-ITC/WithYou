package WithYou.domain.post.dto.response;

import WithYou.domain.post.entity.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailDto {
    private Long id;
    private String title;
    private String content;
    private String userNickName;
    private String userMajor;
    private int userGrade;
    private int commentCount;
    private LocalDateTime createdDate;

    public static PostDetailDto of(Post post) {
        return new PostDetailDto(post.getId(), post.getTitle(), post.getContent(), post.getUserNickName(),
                post.getUserMajor(), post.getUserGrade(), post.getCommentCount(), post.getCreatedDate());
    }
}
