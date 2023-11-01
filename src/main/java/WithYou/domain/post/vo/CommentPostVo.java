package WithYou.domain.post.vo;

import WithYou.domain.comment.dto.response.CommentResponseDto;
import WithYou.domain.post.dto.response.PostLookupDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;

@Getter
@AllArgsConstructor
public class CommentPostVo {
    private List<CommentResponseDto> commentResponseDto;
    private PostLookupDto postLookupDto;
    private ByteArrayResource imageUrl;
}
