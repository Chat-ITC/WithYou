package WithYou.domain.scrap.service;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.ai.repository.AiQueryRepository;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.post.entity.Post;
import WithYou.domain.scrap.dto.response.PostScrapDto;
import WithYou.domain.scrap.entity.Scrap;
import WithYou.domain.scrap.exception.ContentNotFoundException;
import WithYou.domain.scrap.exception.PostExistException;
import WithYou.domain.scrap.exception.PostScrapNotFoundException;
import WithYou.domain.scrap.repository.ScrapQueryRepository;
import WithYou.domain.scrap.repository.ScrapRepository;
import java.util.List;
import java.util.Optional;
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
    private final ScrapRepository scrapRepository;
    private final ScrapQueryRepository scrapQueryRepository;

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

    public void scrapPost(Post post, Member member) {
        Scrap scrap = makeScrapObject(post, member);
        scrapRepository.save(scrap);
    }

    private Scrap makeScrapObject(Post post, Member member) {
        return Scrap.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .member(member)
                .build();
    }

    public List<Scrap> findScrapByMemberId(Member member) {
        List<Scrap> scrapList = scrapQueryRepository.findScrapByMemberIdOrderByIdDESC(member);
        if (scrapList.isEmpty()) {
            throw new PostScrapNotFoundException();
        }
        return scrapList;
    }

    public List<PostScrapDto> changeScrapToDto(List<Scrap> scrapList) {
        return scrapList.stream()
                .map(PostScrapDto::of)
                .collect(Collectors.toList());
    }

    public void checkScrapExist(Member member, Long postId) {
        Optional<Scrap> scrapOptional = scrapQueryRepository.findScrapByMemberIdAndVerify(member, postId);
        if (scrapOptional.isPresent()) {
            throw new PostExistException();
        }
    }

}
