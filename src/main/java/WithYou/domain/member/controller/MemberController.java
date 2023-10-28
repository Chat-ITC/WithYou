package WithYou.domain.member.controller;

import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.member.dto.request.MemberNickNameUpdateDto;
import WithYou.domain.member.service.MemberService;
import WithYou.global.jwt.MemberPrincipal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PatchMapping("/member/update")
    public ResponseEntity<?> updateMemberNickname(
            @RequestBody @Valid MemberNickNameUpdateDto memberNickNameUpdateDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        memberService.updateMemberNickName(memberNickNameUpdateDto.getNickName(), memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body("update 완료");
    }

    @GetMapping("/member/question/list")
    public ResponseEntity<?> findQuestionList(
            @PageableDefault(size = 7, direction = Direction.DESC, sort = "createdDate") Pageable pageable) {
        Page<AiSummaryContent> aiSummaryContents = memberService.findAiSummaryContentList(pageable);
        List<QuestionResponseDto> responseDtoList = aiSummaryContents.getContent()
                .stream()
                .map(QuestionResponseDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(responseDtoList);
    }
}
