package WithYou.domain.member.service;

import WithYou.domain.member.dto.request.MemberSignupDto;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.MemberIdDuplicatedException;
import WithYou.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void isUserIdExists(String userId) {
        Optional<Member> member = memberRepository.findMemberByUserId(userId);
        if (member.isPresent()) {
            throw new MemberIdDuplicatedException();
        }
    }

    @Transactional
    public void signup(MemberSignupDto memberSignupDto) {
        Member member = passwordEncode(memberSignupDto);
        memberRepository.save(member);
    }

    private Member passwordEncode(MemberSignupDto memberSignupDto) {
        String encodePassword = bCryptPasswordEncoder.
                encode(memberSignupDto.getUserPassword());

        Member member = memberSignupDto.toEntity(encodePassword);
        return member;
    }
}
