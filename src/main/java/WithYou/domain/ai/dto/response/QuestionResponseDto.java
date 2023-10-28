package WithYou.domain.ai.dto.response;

import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class QuestionResponseDto {
    private Long id;
    private String title;
    private String content;

    public QuestionResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public AiSummaryContent toEntity(Member member) {
        return AiSummaryContent.builder()
                .id(id)
                .title(title)
                .content(content)
                .member(member)
                .isScrap(IsScrap.No)
                .build();
    }

    public static QuestionResponseDto of(AiSummaryContent aiSummaryContent) {
        return new QuestionResponseDto(aiSummaryContent.getTitle(), aiSummaryContent.getContent());
    }
}
