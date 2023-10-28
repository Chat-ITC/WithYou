package WithYou.domain.ai.exception;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(final String message) {
        super(message);
    }

    public ResultNotFoundException() {
        this("결과값이 존재하지 않습니다.");
    }
}
