package WithYou.domain.ai.service;

import WithYou.domain.ai.dto.request.QuestionRequestDto;
import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.repository.AiQueryRepository;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiService {
    private final AiQueryRepository aiQueryRepository;
    private final AiRepository aiRepository;

    public QuestionRequestDto makeQuestionRequestDto(String ocrResult, String question, Member member) {
        return new QuestionRequestDto(ocrResult, question, member);
    }

    @Transactional
    public void saveSummaryContent(QuestionResponseDto questionResponseDto, Member member) {
        AiSummaryContent aiSummaryContent = questionResponseDto.toEntity(member);

        aiRepository.save(aiSummaryContent);
    }

    public Page<AiSummaryContent> findAiSummaryContentList(Pageable pageable, Member member) {
        return aiQueryRepository.findAiSummaryContentList(pageable, member);
    }

}
