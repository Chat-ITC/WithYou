package WithYou.domain.scrap.dto.response;

import WithYou.domain.scrap.entity.Scrap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostScrapDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime localDateTime;

    public static PostScrapDto of(Scrap scrap) {
        return new PostScrapDto(scrap.getPostId(), scrap.getTitle(), scrap.getContent(),scrap.getCreatedDate());
    }
}
