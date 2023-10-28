package WithYou.domain.member.service;


import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.entity.IsScrap;
import WithYou.domain.ai.repository.AiRepository;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.ContentNotFoundException;
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
    private final AiRepository aiRepository;

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

    public QuestionResponseDto scrapContent(Long id) {
        AiSummaryContent aiSummaryContent = getAiSummaryContent(id);
        aiSummaryContent.setIsScrap(aiSummaryContent.getIsScrap() == IsScrap.No ? IsScrap.YES : IsScrap.No);
        return QuestionResponseDto.of(aiSummaryContent);
    }


    private AiSummaryContent getAiSummaryContent(Long id) {
        return aiRepository.findById(id).orElseThrow(() -> new ContentNotFoundException());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findMemberByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
