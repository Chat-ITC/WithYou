package WithYou.domain.scrap.controller;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.post.entity.Post;
import WithYou.domain.post.service.PostService;
import WithYou.domain.scrap.dto.response.PostScrapDto;
import WithYou.domain.scrap.entity.Scrap;
import WithYou.domain.scrap.service.ScrapService;
import WithYou.global.jwt.MemberPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScrapController {
    private final ScrapService scrapService;
    private final PostService postService;

    @PatchMapping("/scrap/{id}")
    public ResponseEntity<?> scrapHistory(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @PathVariable Long id) {
        QuestionResponseDto questionResponseDto = scrapService.scrapContent(id);
        scrapService.saveSummaryContent(questionResponseDto, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(questionResponseDto.getIsScrap());
    }

    @GetMapping("/scrap/list")
    public ResponseEntity<List<QuestionResponseDto>> getScrapList(
            @AuthenticationPrincipal MemberPrincipal memberPrincipal,
            @PageableDefault(size = 100, direction = Direction.DESC, sort = "lastModifiedDate")
            Pageable pageable) {
        Page<AiSummaryContent> aiSummaryContents = scrapService.findScrapContentList(pageable,
                memberPrincipal.getMember());
        List<QuestionResponseDto> responseDtoList = scrapService.changeToQuestionReponseList(aiSummaryContents);
        return ResponseEntity.ok()
                .body(responseDtoList);
    }

    @PostMapping("/scrap/post/{id}")
    public ResponseEntity<?> scrapPost(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                       @PathVariable Long id) {
        Post post = postService.findPostById(id);
        scrapService.checkScrapExist(memberPrincipal.getMember(), post.getId());
        scrapService.scrapPost(post, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body("post 스크랩 완료");
    }

    @GetMapping("/scrap/post/list")
    public ResponseEntity<?> getScrapPostList(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        List<Scrap> scrapList = scrapService.findScrapByMemberId(memberPrincipal.getMember());
        List<PostScrapDto> postScrapDtos = scrapService.changeScrapToDto(scrapList);
        return ResponseEntity.ok()
                .body(postScrapDtos);
    }
}
