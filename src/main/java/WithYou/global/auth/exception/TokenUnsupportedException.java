package WithYou.global.auth.exception;

public class TokenUnsupportedException extends RuntimeException{
    public TokenUnsupportedException(final String message) {
        super(message);
    }

    public TokenUnsupportedException() {
        this("토큰을 지원 안 합니다.");
    }
}
