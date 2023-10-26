package WithYou.domain.member.exception;

public class MemberPasswordNotFoundException extends RuntimeException{
    public MemberPasswordNotFoundException(final String message) {
        super(message);
    }

    public MemberPasswordNotFoundException() {
        this("비밀번호가 틀렸습니다.");
    }
}
