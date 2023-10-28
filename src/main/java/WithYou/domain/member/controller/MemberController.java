package WithYou.domain.member.controller;

import WithYou.domain.member.dto.request.MemberNickNameUpdateDto;
import WithYou.domain.member.service.MemberService;
import WithYou.global.jwt.MemberPrincipal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

}
