package WithYou.domain.scrap.service;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.scrap.exception.ContentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapService {
    private AiRepository aiRepository;

    public QuestionResponseDto scrapContent(Long id) {
        AiSummaryContent aiSummaryContent = getAiSummaryContent(id);
        aiSummaryContent.setIsScrap(aiSummaryContent.getIsScrap() == IsScrap.No ? IsScrap.YES : IsScrap.No);
        log.info(aiSummaryContent.getIsScrap().toString());
        return QuestionResponseDto.of(aiSummaryContent);
    }

    private AiSummaryContent getAiSummaryContent(Long id) {
        AiSummaryContent aiSummaryContent = aiRepository.findAiSummaryContentById(id)
                .orElseThrow(() -> new ContentNotFoundException());
        log.info("aiSummaryContent 불러오기 성공");
        log.info(aiSummaryContent.getContent());
        return aiSummaryContent;
    }

    @Transactional
    public void saveSummaryContent(QuestionResponseDto questionResponseDto, Member member) {
        AiSummaryContent aiSummaryContent = questionResponseDto.toEntity(member);

        aiRepository.save(aiSummaryContent);
    }


}
