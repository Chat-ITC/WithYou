package WithYou.domain.scrap.exception;


public class PostScrapNotFoundException extends RuntimeException {
    public PostScrapNotFoundException(final String message) {
        super(message);
    }

    public PostScrapNotFoundException() {
        this("스크랩된 게시물을 찾을 수 없습니다.");
    }
}
