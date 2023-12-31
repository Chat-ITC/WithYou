package WithYou.domain.comment.dto.response;

import WithYou.domain.comment.entity.Comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private String content;
    private String userNickName;
    private String userMajor;
    private int userGrade;
    private double userLevel;
    private LocalDateTime createdDate;

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(comment.getContent(), comment.getUserNickName(), comment.getUserMajor(),
                comment.getUserGrade(), comment.getUserLevel(), comment.getCreatedDate());
    }
}
