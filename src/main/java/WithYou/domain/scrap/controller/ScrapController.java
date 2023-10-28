package WithYou.domain.scrap.controller;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.scrap.service.ScrapService;
import WithYou.global.jwt.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScrapController {
    private final ScrapService scrapService;

    @PatchMapping("/scrap/{id}")
    public ResponseEntity<?> scrapHistory(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @PathVariable Long id) {
        QuestionResponseDto questionResponseDto = scrapService.scrapContent(id);
        scrapService.saveSummaryContent(questionResponseDto, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(questionResponseDto.getIsScrap());
    }

//    @GetMapping("/scrap/list")
//    public ResponseEntity<?> getScrapList(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
//                                          @PageableDefault(size = 5, direction = Direction.DESC, sort = "lastModifiedDate")
//                                          Pageable pageable) {
//    }
}
