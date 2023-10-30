package WithYou.domain.comment.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(final String message) {
        super(message);
    }

    public CommentNotFoundException() {
        this("댓글을 찾을 수 없습니다.");
    }
}
