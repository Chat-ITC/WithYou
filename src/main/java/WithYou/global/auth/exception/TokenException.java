package WithYou.global.auth.exception;

import java.awt.TexturePaint;

public class TokenException extends RuntimeException{
    public TokenException(final String message) {
        super(message);
    }

    public TokenException() {
        this("토큰 검증 중에 오류가 발생했습니다.");
    }
}
