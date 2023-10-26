package WithYou.global.auth.controller;

import WithYou.domain.member.entity.Member;
import WithYou.global.auth.dto.MemberLoginDto;
import WithYou.global.auth.dto.MemberSignupDto;
import WithYou.global.auth.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/member/signup")
    public ResponseEntity<String> memberSignup(@RequestBody @Valid MemberSignupDto memberSignupDto) {
        authService.signUp(memberSignupDto);

        return ResponseEntity.ok("회원가입을 성공적으로 마쳤습니다.");
    }

    @PostMapping("/member/signup/userId")
    public ResponseEntity<?> validateUserId(@RequestParam String userId) {
        authService.isUserIdExists(userId);
        return ResponseEntity.ok()
                .body("중복되는 아이디가 없습니다.");
    }

    @PostMapping("/member/signup/nickName")
    public ResponseEntity<?> validateNickName(@RequestParam String nickName) {
        authService.isNickNameExists(nickName);
        return ResponseEntity.ok()
                .body("중복되는 닉네임이 없습니다.");
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
