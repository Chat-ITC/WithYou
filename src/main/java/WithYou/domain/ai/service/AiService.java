package WithYou.domain.ai.service;

import WithYou.domain.ai.dto.request.QuestionRequestDto;
import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.repository.AiQueryRepository;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.service.MemberService;
import WithYou.domain.scrap.exception.ContentNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiService {
    private final MemberService memberService;
    private final AiQueryRepository aiQueryRepository;
    private final AiRepository aiRepository;

    public QuestionRequestDto makeQuestionRequestDto(String ocrResult, String question, String field) {
        return new QuestionRequestDto(ocrResult, question, field);
    }

    @Transactional
    public void saveSummaryContent(QuestionResponseDto questionResponseDto, Member member) {
        AiSummaryContent aiSummaryContent = questionResponseDto.toEntity(member);
        memberService.upgradeMemberLevelUp(member);
        aiRepository.save(aiSummaryContent);
    }

    public Page<AiSummaryContent> findAiSummaryContentList(Pageable pageable, Member member) {
        return aiQueryRepository.findAiSummaryContentList(pageable, member);
    }

    public List<QuestionResponseDto> changeToQuestionReponseList(Page<AiSummaryContent> page) {
        return page.getContent()
                .stream()
                .map(QuestionResponseDto::of)
                .collect(Collectors.toList());
    }

    public AiSummaryContent getQuestion(Long id) {
        return aiRepository.findById(id)
                .orElseThrow(ContentNotFoundException::new);
    }

    public void deleteQuestion(Long id) {
        AiSummaryContent aiSummaryContent = getQuestion(id);
        aiRepository.delete(aiSummaryContent);
    }
}
