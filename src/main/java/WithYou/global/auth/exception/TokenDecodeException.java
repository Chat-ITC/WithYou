package WithYou.global.auth.exception;

public class TokenDecodeException extends RuntimeException {
    public TokenDecodeException(final String message) {
        super(message);
    }

    public TokenDecodeException() {
        this("Token Decode 과정에서 에러가 생겼습니다.");
    }
}
