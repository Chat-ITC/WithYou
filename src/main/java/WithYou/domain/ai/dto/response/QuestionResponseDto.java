package WithYou.domain.ai.dto.response;

import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {
    private Long id;
    private String title;
    private String content;
    private IsScrap isScrap;

    public QuestionResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public QuestionResponseDto(String title, String content, IsScrap isScrap) {
        this.title = title;
        this.content = content;
        this.isScrap = isScrap;
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

    public AiSummaryContent toEntityWithScrap(Member member) {
        return AiSummaryContent.builder()
                .id(id)
                .title(title)
                .content(content)
                .member(member)
                .isScrap(isScrap)
                .build();
    }

    public static QuestionResponseDto of(AiSummaryContent aiSummaryContent) {
        return new QuestionResponseDto(aiSummaryContent.getId(), aiSummaryContent.getTitle(),
                aiSummaryContent.getContent(),
                aiSummaryContent.getIsScrap());
    }
}
