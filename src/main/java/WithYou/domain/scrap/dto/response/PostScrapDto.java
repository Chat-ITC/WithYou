package WithYou.domain.scrap.dto.response;

import WithYou.domain.scrap.entity.Scrap;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostScrapDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private String imageUrl;

    public static PostScrapDto of(Scrap scrap) {
        return new PostScrapDto(scrap.getPostId(), scrap.getTitle(), scrap.getContent(), scrap.getCreatedDate(),
                scrap.getImageUrl());
    }
}
