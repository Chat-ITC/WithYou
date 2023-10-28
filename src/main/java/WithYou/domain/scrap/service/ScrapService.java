package WithYou.domain.scrap.service;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.scrap.exception.ContentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private AiRepository aiRepository;

    public QuestionResponseDto scrapContent(Long id) {
        AiSummaryContent aiSummaryContent = getAiSummaryContent(id);
        aiSummaryContent.setIsScrap(aiSummaryContent.getIsScrap() == IsScrap.No ? IsScrap.YES : IsScrap.No);
        return QuestionResponseDto.of(aiSummaryContent);
    }

    private AiSummaryContent getAiSummaryContent(Long id) {
        AiSummaryContent aiSummaryContent = aiRepository.findAiSummaryContentById(id)
                .orElseThrow(() -> new ContentNotFoundException());
        return aiSummaryContent;
    }

    @Transactional
    public void saveSummaryContent(QuestionResponseDto questionResponseDto, Member member) {
        AiSummaryContent aiSummaryContent = questionResponseDto.toEntity(member);

        aiRepository.save(aiSummaryContent);
    }


}
