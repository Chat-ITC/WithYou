package WithYou.domain.scrap.dto.response;

import WithYou.domain.scrap.entity.Scrap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostScrapDto {
    private Long postId;
    private String title;
    private String content;

    public static PostScrapDto of(Scrap scrap) {
        return new PostScrapDto(scrap.getPostId(), scrap.getTitle(), scrap.getContent());
    }
}
