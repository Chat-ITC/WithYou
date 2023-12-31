package WithYou.domain.member.service;


import WithYou.domain.member.dto.response.MemberMyPageDto;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.MemberNickNameDulicatedException;
import WithYou.domain.member.repository.MemberQueryRepository;
import WithYou.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public void updateMemberNickName(String nickName, Member member) {
        checkNicknameDuplicated(nickName);
        Member updateMember = Member.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPassword(member.getUserPassword())
                .level(member.getLevel())
                .grade(member.getGrade())
                .major(member.getMajor())
                .nickName(nickName)
                .build();

        memberRepository.save(updateMember);
    }

    private void checkNicknameDuplicated(String nickName) {
        Optional<Member> member = memberRepository.findMemberByNickName(nickName);
        if (member.isPresent()) {
            throw new MemberNickNameDulicatedException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findMemberByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public MemberMyPageDto changeMemberToDto(Member member) {
        return MemberMyPageDto.of(member);
    }


    public void upgradeMemberLevelUp(Member member) {
        member.upgradeMemberLevel();
        memberRepository.save(member);
    }
}
