package WithYou.domain.comment.dto.response;

import WithYou.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private String content;
    private String userNickName;
    private String userMajor;
    private int userGrade;

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(comment.getContent(), comment.getUserNickName(), comment.getUserMajor(),
                comment.getUserGrade());
    }
}
