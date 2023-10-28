package WithYou.domain.member.controller;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.service.AiService;
import WithYou.domain.member.dto.request.MemberNickNameUpdateDto;
import WithYou.domain.member.service.MemberService;
import WithYou.global.jwt.MemberPrincipal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AiService aiService;

    @PatchMapping("/member/update")
    public ResponseEntity<?> updateMemberNickname(
            @RequestBody @Valid MemberNickNameUpdateDto memberNickNameUpdateDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        memberService.updateMemberNickName(memberNickNameUpdateDto.getNickName(), memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body("update 완료");
    }

    @GetMapping("/member/scrap/{id}")
    public ResponseEntity<?> scrapHistory(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @PathVariable Long id) {
        QuestionResponseDto questionResponseDto = memberService.scrapContent(id);
        aiService.saveSummaryContent(questionResponseDto, memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(questionResponseDto.getIsScrap());
    }

}
