package WithYou.domain.member.controller;

import WithYou.domain.ai.service.AiService;
import WithYou.domain.member.dto.request.MemberNickNameUpdateDto;
import WithYou.domain.member.dto.response.MemberMyPageDto;
import WithYou.domain.member.service.MemberService;
import WithYou.global.jwt.MemberPrincipal;
import javax.validation.Valid;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    private final AiService aiService;

    @GetMapping("/hello")
    public ResponseEntity<String> rootController(){
        return ResponseEntity.ok("연결성공");
    }

    @PatchMapping("/member/update")
    public ResponseEntity<String> updateMemberNickname(
            @RequestBody @Valid MemberNickNameUpdateDto memberNickNameUpdateDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        memberService.updateMemberNickName(memberNickNameUpdateDto.getNickName(), memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body("update 완료");
    }

    @GetMapping("/member/mypage")
    public ResponseEntity<MemberMyPageDto> myPage(
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        MemberMyPageDto memberInfo = memberService.changeMemberToDto(memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body(memberInfo);
    }
}
