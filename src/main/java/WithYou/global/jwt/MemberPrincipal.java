package WithYou.global.jwt;

import WithYou.domain.member.entity.Member;
import java.io.Serializable;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberPrincipal extends User implements Serializable {
	private final Member member;

	public MemberPrincipal(Member member) {
		super(member.getUsername(), member.getPassword(),
			member.getAuthorities()); // nickname, password, authorities(null);
		this.member = member; // 인증 받은 멤버를 반환할 수 있음
	}
}
