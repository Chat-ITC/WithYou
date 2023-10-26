package WithYou.global.auth.service;

import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.MemberIdDuplicatedException;
import WithYou.domain.member.exception.MemberIdNotFoundException;
import WithYou.domain.member.exception.MemberNickNameDulicatedException;
import WithYou.domain.member.exception.MemberPasswordNotFoundException;
import WithYou.domain.member.repository.MemberRepository;
import WithYou.global.auth.dto.MemberLoginDto;
import WithYou.global.auth.dto.MemberSignupDto;
import WithYou.global.jwt.TokenProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    public void isUserIdExists(String userId) {
        Optional<Member> member = memberRepository.findMemberByUserId(userId);
        if (member.isPresent()) {
            throw new MemberIdDuplicatedException();
        }
    }

    public void isNickNameExists(String nickName) {
        Optional<Member> member = memberRepository.findMemberByNickName(nickName);
        if (member.isPresent()) {
            throw new MemberNickNameDulicatedException();
        }
    }

    //회원가입
    @Transactional
    public void signUp(MemberSignupDto memberSignupDto) {
        Member member = passwordEncode(memberSignupDto);
        memberRepository.save(member);
    }

    private Member passwordEncode(MemberSignupDto memberSignupDto) {
        String encodePassword = bCryptPasswordEncoder
                .encode(memberSignupDto.getUserPassword());

        Member member = memberSignupDto.toEntity(encodePassword);
        return member;
    }

    //로그인
    public Member signIn(MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findMemberByUserId(memberLoginDto.getUserId())
                .orElseThrow(() -> new MemberIdNotFoundException());

        if (!bCryptPasswordEncoder.matches(memberLoginDto.getUserPassword(), member.getPassword())) {
            throw new MemberPasswordNotFoundException();
        }
        return member;
    }

    public String createAccessToken(Long id) {
        String accessToken = tokenProvider.createAccessToken(id);
        return accessToken;
    }

    public String createRefreshToken(Long id) {
        String refreshToken = tokenProvider.createRefreshToken(id);
        return refreshToken;
    }
}
