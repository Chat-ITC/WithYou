package WithYou.domain.member.controller;

import WithYou.domain.ai.service.AiService;
import WithYou.domain.member.dto.request.MemberMypageCheckDto;
import WithYou.domain.member.dto.request.MemberNickNameUpdateDto;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.service.MemberService;
import WithYou.global.jwt.MemberPrincipal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

    @GetMapping("/member/mypage")
    public ResponseEntity<?> myPage(
            @AuthenticationPrincipal MemberPrincipal memberPrincipal){
        Optional<Member> memberInfo = memberService.checkMemberInfo(memberPrincipal.getMember());
        if(memberInfo.isPresent()){
            Member member = memberInfo.get();
            MemberMypageCheckDto memberMypageCheckDto = MemberMypageCheckDto.of(member);
            return ResponseEntity.ok().body(memberMypageCheckDto);
        }
        return ResponseEntity.notFound().build();
    }
}
