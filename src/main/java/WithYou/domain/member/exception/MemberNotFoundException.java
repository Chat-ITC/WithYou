package WithYou.domain.member.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(final String message) {
        super(message);
    }

    public MemberNotFoundException() {
        this("멤버를 찾을 수 없습니다.");
    }
}
