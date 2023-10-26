package WithYou.domain.member.repository;

import WithYou.domain.member.entity.Member;
import java.util.Optional;
import java.util.OptionalInt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByUserId(String userId);

    Optional<Member> findMemberByNickName(String nickName);
}
