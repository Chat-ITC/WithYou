package WithYou.global.common.error;

import WithYou.domain.ai.exception.ResultNotFoundException;
import WithYou.domain.member.exception.MemberIdDuplicatedException;
import WithYou.domain.member.exception.MemberIdNotFoundException;
import WithYou.domain.member.exception.MemberNickNameDulicatedException;
import WithYou.domain.member.exception.MemberNotFoundException;
import WithYou.domain.member.exception.MemberPasswordNotFoundException;
import WithYou.domain.post.exception.DepartmentNotMatchException;
import WithYou.domain.post.exception.PostNotFoundException;
import WithYou.domain.scrap.exception.ContentNotFoundException;
import WithYou.global.auth.exception.TokenDecodeException;
import WithYou.global.auth.exception.TokenException;
import WithYou.global.auth.exception.TokenExpiredException;
import WithYou.global.auth.exception.TokenUnsupportedException;
import WithYou.global.common.error.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthorization(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler({
            TokenUnsupportedException.class,
            TokenDecodeException.class,
            MemberIdDuplicatedException.class,
            MemberNickNameDulicatedException.class,
            DepartmentNotMatchException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleInternationalServerError(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler({
            MemberNotFoundException.class,
            MemberIdNotFoundException.class,
            MemberPasswordNotFoundException.class,
            ResultNotFoundException.class,
            ContentNotFoundException.class,
            PostNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
