package WithYou.global.auth.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(final String message) {
        super(message);
    }

    public TokenExpiredException() {
        this("토큰이 만료 되었습니다.");
    }
}
