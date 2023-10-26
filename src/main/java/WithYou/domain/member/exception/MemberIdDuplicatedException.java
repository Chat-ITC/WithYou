package WithYou.domain.member.exception;

public class MemberIdDuplicatedException extends RuntimeException {
    public MemberIdDuplicatedException(final String message) {
        super(message);
    }

    public MemberIdDuplicatedException() {
        this("입력된 ID가 이미 존재합니다.");
    }
}
