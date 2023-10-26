package WithYou.domain.member.exception;

public class MemberIdNotFoundException extends RuntimeException {
    public MemberIdNotFoundException(final String message) {
        super(message);
    }

    public MemberIdNotFoundException() {
        this("ID가 존재하지 않습니다.");
    }
}
