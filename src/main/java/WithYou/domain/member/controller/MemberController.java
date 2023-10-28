package WithYou.domain.member.controller;

import WithYou.domain.member.dto.request.MemberNickNameUpdateDto;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.MemberNotFoundException;
import WithYou.domain.member.repository.MemberRepositorySupport;
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
    private final MemberRepositorySupport memberRepositorySupport;

    @PatchMapping("/member/update")
    public ResponseEntity<?> updateMemberNickname(
            @RequestBody @Valid MemberNickNameUpdateDto memberNickNameUpdateDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        memberService.updateMemberNickName(memberNickNameUpdateDto.getNickName(), memberPrincipal.getMember());
        return ResponseEntity.ok()
                .body("update 완료");
    }

    @GetMapping("/member/find/{id}")
    public ResponseEntity<?> findMemberById(@PathVariable Long id) {
        Member member = memberRepositorySupport.findById(id).orElseThrow(() -> new MemberNotFoundException());
        return ResponseEntity.ok()
                .body(member.getNickName());
    }
}
