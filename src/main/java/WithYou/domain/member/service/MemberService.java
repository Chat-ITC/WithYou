package WithYou.domain.member.service;

import WithYou.domain.member.dto.request.MemberLoginDto;
import WithYou.domain.member.dto.request.MemberSignupDto;
import WithYou.domain.member.entity.Member;
import WithYou.domain.member.exception.MemberIdDuplicatedException;
import WithYou.domain.member.exception.MemberIdNotFoundException;
import WithYou.domain.member.exception.MemberPasswordNotFoundException;
import WithYou.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

}
