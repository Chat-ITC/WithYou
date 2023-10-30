package WithYou.domain.post.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(final String message) {
        super(message);
    }

    public PostNotFoundException() {
        super("Post를 찾을 수 없습니다.");
    }
}
