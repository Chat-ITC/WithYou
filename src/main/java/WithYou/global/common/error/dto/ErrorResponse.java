package WithYou.global.common.error.dto;

public class ErrorResponse {
    private final String message;
    public ErrorResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
