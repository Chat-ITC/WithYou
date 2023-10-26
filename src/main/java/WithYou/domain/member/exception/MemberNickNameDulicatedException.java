package WithYou.domain.member.exception;

public class MemberNickNameDulicatedException extends RuntimeException {
    public MemberNickNameDulicatedException(final String message) {
        super(message);
    }

    public MemberNickNameDulicatedException() {
        this("닉네임이 이미 존재합니다.");
    }
}
