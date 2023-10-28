package WithYou.domain.member.exception;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(final String message) {
        super(message);
    }

    public ContentNotFoundException() {
        this("히스토리를 찾을 수 없습니다.");
    }
}
