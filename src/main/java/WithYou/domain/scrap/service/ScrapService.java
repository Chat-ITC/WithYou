package WithYou.domain.scrap.service;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.ai.repository.AiQueryRepository;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.scrap.exception.ContentNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapService {
    private final AiRepository aiRepository;
    private final AiQueryRepository aiQueryRepository;

    public QuestionResponseDto scrapContent(Long id) {
        AiSummaryContent aiSummaryContent = getAiSummaryContent(id);
        aiSummaryContent.setIsScrap(aiSummaryContent.getIsScrap() == IsScrap.No ? IsScrap.YES : IsScrap.No);
        return QuestionResponseDto.of(aiSummaryContent);
    }

    private AiSummaryContent getAiSummaryContent(Long id) {
        return aiRepository.findAiSummaryContentById(id)
                .orElseThrow(() -> new ContentNotFoundException());
    }

    @Transactional
    public void saveSummaryContent(QuestionResponseDto questionResponseDto, Member member) {
        AiSummaryContent aiSummaryContent = questionResponseDto.toEntityWithScrap(member);
        aiRepository.save(aiSummaryContent);
    }

    public Page<AiSummaryContent> findScrapContentList(Pageable pageable, Member member) {
        return aiQueryRepository.findScrapContentList(pageable, member);
    }

    public List<QuestionResponseDto> changeToQuestionReponseList(Page<AiSummaryContent> page) {
        return page.getContent()
                .stream()
                .map(QuestionResponseDto::of)
                .collect(Collectors.toList());
    }

}
