package WithYou.domain.member.controller;

import WithYou.domain.member.dto.request.MemberSignupDto;
import WithYou.domain.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<?> memberSignup(@RequestBody @Valid MemberSignupDto signupDto) {
        memberService.isUserIdExists(signupDto.getUserId()); // userId 중복 검사
        memberService.signup(signupDto);

        return ResponseEntity.ok("회원가입을 성공적으로 마쳤습니다.");
    }
}
