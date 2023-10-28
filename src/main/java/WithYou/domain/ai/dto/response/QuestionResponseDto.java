package WithYou.domain.ai.dto.response;

import WithYou.domain.ai.entity.AiSummaryContent;
import lombok.Getter;

@Getter
public class QuestionResponseDto {
    private String title;
    private String content;

    public QuestionResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public AiSummaryContent toEntity() {
        return AiSummaryContent.builder()
                .title(title)
                .content(content)
                .build();
    }
}
