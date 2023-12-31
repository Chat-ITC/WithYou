package WithYou.domain.member.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(final String message) {
        super(message);
    }

    public MemberNotFoundException() {
        this("멤버를 찾을 수 없습니다.");
    }
}
