package WithYou.domain.post.dto.response;

import WithYou.domain.post.entity.Post;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLookupDto {
    private Long id;
    private String title;
    private String content;
    private String userNickName;
    private String userMajor;
    private int userGrade;
    private int commentCount;
    private double userLevel;
    private LocalDateTime createdDate;
    private String imageUrl;

    public static PostLookupDto of(Post post) {
        return new PostLookupDto(post.getId(), post.getTitle(), post.getContent(), post.getUserNickName(),
                post.getUserMajor(), post.getUserGrade(), post.getCommentCount(), post.getUserLevel(), post.getCreatedDate(),
                post.getImageUrl());
    }
}
