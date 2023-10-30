package WithYou.domain.post.vo;

import WithYou.domain.comment.dto.response.CommentResponseDto;
import WithYou.domain.post.dto.response.PostLookupDto;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentPostValueObject {
    private List<CommentResponseDto> commentResponseDto;
    private PostLookupDto postLookupDto;
}
