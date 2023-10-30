package WithYou.domain.post.exception;

public class DepartmentNotMatchException extends RuntimeException {
    public DepartmentNotMatchException(final String message) {
        super(message);
    }

    public DepartmentNotMatchException() {
        this("학과가 일치하지 않습니다.");
    }
}
