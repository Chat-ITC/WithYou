package WithYou.global.auth.controller;

import WithYou.global.auth.dto.MemberLoginDto;
import WithYou.global.auth.dto.MemberSignupDto;
import WithYou.domain.member.entity.Member;
import WithYou.global.auth.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/member/signup")
    public ResponseEntity<String> memberSignup(@RequestBody @Valid MemberSignupDto memberSignupDto) {
        authService.isUserIdExists(memberSignupDto.getUserId()); // userId 중복 검사
        authService.isNickNameExists(memberSignupDto.getNickName()); //nickname 중복 검사
        authService.signUp(memberSignupDto);

        return ResponseEntity.ok("회원가입을 성공적으로 마쳤습니다.");
    }

    @PostMapping("/member/signin")
    public ResponseEntity<?> memberSignin(@RequestBody @Valid MemberLoginDto memberLoginDto) {
        Member member = authService.signIn(memberLoginDto);
        String accessToken = authService.createAccessToken(member.getId());
        String refreshToken = authService.createRefreshToken(member.getId());

        return ResponseEntity.ok()
                .header("refreshToken", refreshToken)
                .header("accessToken", accessToken)
                .body("로그인을 성공적으로 마쳤습니다.");
    }
}
