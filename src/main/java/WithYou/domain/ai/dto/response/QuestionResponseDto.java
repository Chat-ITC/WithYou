package WithYou.domain.ai.dto.response;

import WithYou.domain.ai.entity.AiSummaryCode;
import lombok.Getter;

@Getter
public class QuestionResponseDto {
    private String title;
    private String content;

    public QuestionResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public AiSummaryCode toEntity() {
        return AiSummaryCode.builder()
                .title(title)
                .content(content)
                .build();
    }
}
