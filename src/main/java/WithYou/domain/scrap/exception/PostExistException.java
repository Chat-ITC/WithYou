package WithYou.domain.scrap.exception;

public class PostExistException extends RuntimeException {
    public PostExistException(final String message) {
        super(message);
    }

    public PostExistException() {
        this("이미 POST가 Scrap에 존재합니다.");
    }
}
