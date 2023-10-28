package WithYou.domain.ai.service;

import WithYou.domain.ai.dto.request.QuestionRequestDto;
import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryCode;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiService {
    private final AiRepository aiRepository;

    public QuestionRequestDto makeQuestionRequestDto(String ocrResult, String question, Member member) {
        return new QuestionRequestDto(ocrResult, ocrResult, member);
    }

    @Transactional
    public void saveSummaryContent(QuestionResponseDto questionResponseDto) {
        AiSummaryCode aiSummaryCode = questionResponseDto.toEntity();

        aiRepository.save(aiSummaryCode);
    }

}
